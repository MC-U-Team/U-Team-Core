package info.u_team.u_team_core.util.annotation;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import info.u_team.u_team_core.util.ClassUtil;
import net.fabricmc.loader.impl.util.ExceptionUtil;

/**
 * Annotation utility methods
 *
 * @author HyCraftHD
 */
public class AnnotationUtil {
	
	/**
	 * Returns a list of found {@link AnnotationData} for the give annotation {@link Type} and modid.
	 *
	 * @param modid The mod files to search for annotations
	 * @param type Annotation type
	 * @return List of found annotation data
	 */
	public static List<AnnotationData> getAnnotations(String modid, Type type) {
		final Set<Path> paths = ClassUtil.findModClasses(modid);
		
		final List<AnnotationData> annotations = new ArrayList<>();
		
		for (final Path path : paths) {
			try (final InputStream inputStream = Files.newInputStream(path)) {
				final ClassReader classReader = new ClassReader(inputStream);
				final AnnotationClassVisitor visitor = new AnnotationClassVisitor();
				classReader.accept(visitor, 0);
				annotations.addAll(visitor.annotations);
			} catch (final IOException ex) {
				throw ExceptionUtil.wrap(ex);
			}
		}
		
		return annotations.stream() //
				.filter(data -> type.equals(data.annotationType())) //
				.collect(Collectors.toList());
	}
	
	public record AnnotationData(Type annotationType, ElementType targetType, Type clazz, String memberName, Map<String, Object> annotationData) {
	}
	
	public record EnumValue(Type clazz, String value) {
	}
	
	private static class AnnotationClassVisitor extends ClassVisitor {
		
		private final Set<AnnotationData> annotations = new HashSet<>();
		
		private Type type;
		
		private AnnotationClassVisitor() {
			super(Opcodes.ASM9);
		}
		
		@Override
		public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
			type = Type.getObjectType(name);
		}
		
		@Override
		public AnnotationVisitor visitAnnotation(String annotationDescriptor, boolean visible) {
			return new OurAnnotationVisitor(new AnnotationData(Type.getType(annotationDescriptor), ElementType.TYPE, type, type.getClassName(), new HashMap<>()));
		}
		
		@Override
		public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
			return new FieldVisitor(Opcodes.ASM9) {
				
				@Override
				public AnnotationVisitor visitAnnotation(String annotationDescriptor, boolean visible) {
					return new OurAnnotationVisitor(new AnnotationData(Type.getType(annotationDescriptor), ElementType.FIELD, type, name, new HashMap<>()));
				}
			};
		}
		
		@Override
		public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
			return new MethodVisitor(Opcodes.ASM9) {
				
				@Override
				public AnnotationVisitor visitAnnotation(String annotationDescriptor, boolean visible) {
					return new OurAnnotationVisitor(new AnnotationData(Type.getType(annotationDescriptor), ElementType.METHOD, type, name + descriptor, new HashMap<>()));
				}
			};
		}
		
		private class OurAnnotationVisitor extends AnnotationVisitor {
			
			private final DataHolder data;
			
			private OurAnnotationVisitor(AnnotationData annotationData) {
				super(Opcodes.ASM9);
				data = new DataHolder(annotationData.annotationData);
				annotations.add(annotationData);
			}
			
			private OurAnnotationVisitor(DataHolder data) {
				super(Opcodes.ASM9);
				this.data = data;
			}
			
			@Override
			public void visit(String name, Object value) {
				data.add(name, value);
			}
			
			@Override
			public void visitEnum(String name, String descriptor, String value) {
				data.add(name, new EnumValue(Type.getType(descriptor), value));
			}
			
			@Override
			public AnnotationVisitor visitArray(String name) {
				return new OurAnnotationVisitor(data.addList(name));
			}
			
			@Override
			public AnnotationVisitor visitAnnotation(String name, String descriptor) {
				return new OurAnnotationVisitor(data.addMap(name));
			}
			
		}
		
		private class DataHolder {
			
			private final Map<String, Object> map;
			private final List<Object> list;
			
			private DataHolder(Map<String, Object> map) {
				this.map = map;
				this.list = null;
			}
			
			private DataHolder(List<Object> list) {
				this.map = null;
				this.list = list;
			}
			
			private void add(String name, Object value) {
				if (map != null) {
					map.put(name, value);
				}
				if (list != null) {
					list.add(value);
				}
			}
			
			private DataHolder addList(String name) {
				final List<Object> subList = new ArrayList<>();
				add(name, subList);
				return new DataHolder(subList);
			}
			
			private DataHolder addMap(String name) {
				final Map<String, Object> subMap = new HashMap<>();
				add(name, subMap);
				return new DataHolder(subMap);
			}
			
		}
	}
}

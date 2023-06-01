package info.u_team.u_team_core.impl;

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

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import info.u_team.u_team_core.util.ClassUtil;
import info.u_team.u_team_core.util.annotation.AnnotationUtil;
import info.u_team.u_team_core.util.annotation.AnnotationUtil.AnnotationData;
import net.fabricmc.loader.impl.util.ExceptionUtil;

public class FabricAnnotationScanner implements AnnotationUtil.AnnotationScanner {
	
	private final Map<String, List<AnnotationData>> data = new HashMap<>();
	
	@Override
	public List<AnnotationData> findAnnotations(String modid) {
		return data.computeIfAbsent(modid, this::scanMod);
	}
	
	private List<AnnotationData> scanMod(String modid) {
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
		
		return annotations;
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
			return new OurAnnotationVisitor(annotationDescriptor, ElementType.TYPE, type, type.getClassName());
		}
		
		@Override
		public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
			return new FieldVisitor(Opcodes.ASM9) {
				
				@Override
				public AnnotationVisitor visitAnnotation(String annotationDescriptor, boolean visible) {
					return new OurAnnotationVisitor(annotationDescriptor, ElementType.FIELD, type, name);
				}
			};
		}
		
		@Override
		public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
			return new MethodVisitor(Opcodes.ASM9) {
				
				@Override
				public AnnotationVisitor visitAnnotation(String annotationDescriptor, boolean visible) {
					return new OurAnnotationVisitor(annotationDescriptor, ElementType.METHOD, type, name + descriptor);
				}
			};
		}
		
		private class OurAnnotationVisitor extends AnnotationVisitor {
			
			private final DataHolder data;
			
			private OurAnnotationVisitor(String annotationDescriptor, ElementType targetType, Type type, String memberName) {
				super(Opcodes.ASM9);
				final AnnotationData annotationData = new AnnotationData(Type.getType(annotationDescriptor), targetType, type, memberName, new HashMap<>());
				data = new DataHolder(annotationData.annotationData());
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

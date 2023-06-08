package info.u_team.u_team_core.util.annotation;

import java.lang.annotation.ElementType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.objectweb.asm.Type;

import info.u_team.u_team_core.util.ServiceUtil;

/**
 * Annotation utility methods
 *
 * @author HyCraftHD
 */
public class AnnotationUtil {
	
	private static final AnnotationScanner SCANNER = ServiceUtil.loadOne(AnnotationScanner.class);
	
	/**
	 * Returns a list of found {@link AnnotationData} for the give annotation {@link Type} and modid.
	 *
	 * @param modid The mod files to search for annotations
	 * @param type Annotation type
	 * @return List of found annotation data
	 */
	public static List<AnnotationData> getAnnotations(String modid, Type type) {
		return getAnnotations(modid).stream() //
				.filter(data -> type.equals(data.annotationType())) //
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns a list of found {@link AnnotationData} for a modid
	 *
	 * @param modid The mod files to search for annotations
	 * @return List of found annotation data
	 */
	public static List<AnnotationData> getAnnotations(String modid) {
		return SCANNER.findAnnotations(modid);
	}
	
	public static record AnnotationData(Type annotationType, ElementType targetType, Type clazz, String memberName, Map<String, Object> annotationData) {
	}
	
	public static interface AnnotationScanner {
		
		List<AnnotationData> findAnnotations(String modid);
	}
	
}

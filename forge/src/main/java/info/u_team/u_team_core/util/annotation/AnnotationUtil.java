package info.u_team.u_team_core.util.annotation;

import java.util.List;
import java.util.stream.Collectors;

import org.objectweb.asm.Type;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;

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
		return ModList.get() //
				.getModFileById(modid) //
				.getFile() //
				.getScanResult() //
				.getAnnotations() //
				.stream() //
				.filter(data -> type.equals(data.annotationType())) //
				.collect(Collectors.toList());
	}
	
}

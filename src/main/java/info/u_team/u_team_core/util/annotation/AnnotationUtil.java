package info.u_team.u_team_core.util.annotation;

import java.util.List;
import java.util.stream.Collectors;

import org.objectweb.asm.Type;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;

public class AnnotationUtil {
	
	public static List<AnnotationData> getAnnotations(String modid, Type type) {
		return ModList.get() //
				.getModFileById(modid) //
				.getFile() //
				.getScanResult() //
				.getAnnotations() //
				.stream() //
				.filter(data -> type.equals(data.getAnnotationType())) //
				.collect(Collectors.toList());
	}
	
}
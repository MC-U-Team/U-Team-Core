package info.u_team.u_team_core.impl;

import java.util.List;
import java.util.stream.Collectors;

import info.u_team.u_team_core.util.annotation.AnnotationUtil;
import info.u_team.u_team_core.util.annotation.AnnotationUtil.AnnotationData;
import net.neoforged.fml.ModList;

public class NeoForgeAnnotationScanner implements AnnotationUtil.AnnotationScanner {
	
	@Override
	public List<AnnotationData> findAnnotations(String modid) {
		return ModList.get() //
				.getModFileById(modid) //
				.getFile() //
				.getScanResult() //
				.getAnnotations() //
				.stream() //
				.map(data -> new AnnotationData(data.annotationType(), data.targetType(), data.clazz(), data.memberName(), data.annotationData())) //
				.collect(Collectors.toList());
	}
	
}

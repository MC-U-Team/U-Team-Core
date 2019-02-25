package info.u_team.u_team_core.registry;

import java.util.Collection;
import java.util.stream.*;

import info.u_team.u_team_core.item.tool.ToolSet;
import info.u_team.u_team_core.util.RegistryUtil;

public class ToolSetRegistry {
	
	public static void register(String modid, ToolSet toolset) {
		ItemRegistry.register(modid, Stream.of(toolset.getItemArray()).filter(entry -> entry != null).collect(Collectors.toList()));
	}
	
	public static void register(String modid, Collection<ToolSet> list) {
		list.forEach(item -> register(modid, item));
	}
	
	public static void register(String modid, Class<?> clazz) {
		register(modid, RegistryUtil.getClassEntries(ToolSet.class, clazz));
	}
	
}

package info.u_team.u_team_core.registry;

import java.util.List;

import info.u_team.u_team_core.api.registry.IURegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

class BaseRegistry {
	
	static <T extends IForgeRegistryEntry<T>> void register(String modid, T entry, List<T> list) {
		if (entry instanceof IURegistry) {
			IURegistry uentry = (IURegistry) entry;
			entry.setRegistryName(new ResourceLocation(modid, uentry.getEntryName()));
		}
		list.add(entry);
	}
	
}

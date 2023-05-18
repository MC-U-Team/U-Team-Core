package info.u_team.u_team_core.impl;

import java.util.Map.Entry;
import java.util.function.Consumer;

import info.u_team.u_team_core.api.registry.CreativeModeTabRegister;
import info.u_team.u_team_core.impl.common.CommonCreativeModeTabRegister;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.world.item.CreativeModeTab;

public class FabricCreativeModeTabRegister extends CommonCreativeModeTabRegister {
	
	FabricCreativeModeTabRegister(String modid) {
		super(modid);
	}
	
	@Override
	public void register() {
		for (final Entry<CreativeModeTabEntry, Consumer<CreativeModeTab.Builder>> entry : entries.entrySet()) {
			final CreativeModeTabEntry registryEntry = entry.getKey();
			
			final CreativeModeTab.Builder builder = FabricItemGroup.builder(registryEntry.getId());
			applyBuilderDefaults(registryEntry, builder);
			entry.getValue().accept(builder);
			
			final CreativeModeTab tab = builder.build();
			updateReference(registryEntry, tab);
		}
	}
	
	public static class Factory implements CreativeModeTabRegister.Factory {
		
		@Override
		public CreativeModeTabRegister create(String modid) {
			return new FabricCreativeModeTabRegister(modid);
		}
	}
}

package info.u_team.u_team_core.impl;

import java.util.Map.Entry;
import java.util.function.Consumer;

import info.u_team.u_team_core.api.registry.CreativeModeTabRegister;
import info.u_team.u_team_core.impl.common.CommonCreativeModeTabRegister;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.CreativeModeTabEvent;

public class ForgeCreativeModeTabRegister extends CommonCreativeModeTabRegister {
	
	ForgeCreativeModeTabRegister(String modid) {
		super(modid);
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> bus.addListener(this::registerTab));
	}
	
	private void registerTab(CreativeModeTabEvent.Register event) {
		for (final Entry<CreativeModeTabEntry, Consumer<CreativeModeTab.Builder>> entry : entries.entrySet()) {
			final CreativeModeTabEntry registryEntry = entry.getKey();
			
			final CreativeModeTab tab = event.registerCreativeModeTab(registryEntry.getId(), builder -> {
				applyBuilderDefaults(registryEntry, builder);
				entry.getValue().accept(builder);
			});
			updateReference(registryEntry, tab);
		}
	}
	
	public static class Factory implements CreativeModeTabRegister.Factory {
		
		@Override
		public CreativeModeTabRegister create(String modid) {
			return new ForgeCreativeModeTabRegister(modid);
		}
	}
}

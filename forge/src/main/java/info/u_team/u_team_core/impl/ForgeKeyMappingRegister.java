package info.u_team.u_team_core.impl;

import java.util.Map.Entry;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.client.KeyMappingRegister;
import info.u_team.u_team_core.impl.common.CommonKeyMappingRegister;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;

public class ForgeKeyMappingRegister extends CommonKeyMappingRegister {
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> bus.addListener(this::registerKeyMapping));
	}
	
	private void registerKeyMapping(RegisterKeyMappingsEvent event) {
		for (final Entry<ForgeKeyMappingSimpleEntry, Supplier<KeyMapping>> entry : entries.entrySet()) {
			final ForgeKeyMappingSimpleEntry registryEntry = entry.getKey();
			final KeyMapping keyMapping = entry.getValue().get();
			event.register(keyMapping);
			updateReference(registryEntry, keyMapping);
		}
	}
	
	public static class Factory implements KeyMappingRegister.Factory {
		
		@Override
		public KeyMappingRegister create() {
			return new ForgeKeyMappingRegister();
		}
	}
}

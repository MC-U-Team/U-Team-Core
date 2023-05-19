package info.u_team.u_team_core.impl;

import java.util.Map.Entry;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.client.KeyMappingRegister;
import info.u_team.u_team_core.impl.common.CommonKeyMappingRegister;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class FabricKeyMappingRegister extends CommonKeyMappingRegister {
	
	@Override
	public void register() {
		for (final Entry<ForgeKeyMappingSimpleEntry, Supplier<KeyMapping>> entry : entries.entrySet()) {
			final ForgeKeyMappingSimpleEntry registryEntry = entry.getKey();
			final KeyMapping keyMapping = entry.getValue().get();
			KeyBindingHelper.registerKeyBinding(keyMapping);
			updateReference(registryEntry, keyMapping);
		}
	}
	
	public static class Factory implements KeyMappingRegister.Factory {
		
		@Override
		public KeyMappingRegister create() {
			return new FabricKeyMappingRegister();
		}
	}
}

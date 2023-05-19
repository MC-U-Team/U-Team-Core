package info.u_team.u_team_core.impl;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.registry.client.KeyMappingRegister;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class FabricKeyMappingRegister implements KeyMappingRegister {
	
	@Override
	public Supplier<KeyMapping> registerKeyMapping(Supplier<KeyMapping> supplier) {
		final Supplier<KeyMapping> memoized = Suppliers.memoize(supplier::get);
		KeyBindingHelper.registerKeyBinding(memoized.get());
		return memoized;
	}
}

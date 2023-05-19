package info.u_team.u_team_core.impl;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.registry.client.KeyMappingRegister;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class ForgeKeyMappingRegister implements KeyMappingRegister {
	
	@Override
	public Supplier<KeyMapping> registerKeyMapping(Supplier<KeyMapping> supplier) {
		final Supplier<KeyMapping> memoized = Suppliers.memoize(supplier::get);
		BusRegister.registerMod(bus -> bus.addListener(EventPriority.NORMAL, false, RegisterKeyMappingsEvent.class, event -> {
			event.register(memoized.get());
		}));
		return memoized;
	}
}

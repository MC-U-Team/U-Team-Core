package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.EntityAttributeRegister;
import info.u_team.u_team_core.impl.common.CommonEntityAttributeRegister;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

public class ForgeEntityAttributeRegister extends CommonEntityAttributeRegister {
	
	ForgeEntityAttributeRegister() {
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> bus.addListener(this::entityAttributionCreation));
	}
	
	private void entityAttributionCreation(EntityAttributeCreationEvent event) {
		attributes.forEach((supplier, map) -> {
			event.put(supplier.get(), map.get());
		});
	}
	
	public static class Factory implements EntityAttributeRegister.Factory {
		
		@Override
		public EntityAttributeRegister create() {
			return new ForgeEntityAttributeRegister();
		}
	}
	
}

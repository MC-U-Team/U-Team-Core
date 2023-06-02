package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.EntityAttributeRegister;
import info.u_team.u_team_core.event.SetupEvents;
import info.u_team.u_team_core.impl.common.CommonEntityAttributeRegister;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class FabricEntityAttributeRegister extends CommonEntityAttributeRegister {
	
	FabricEntityAttributeRegister() {
	}
	
	@Override
	public void register() {
		SetupEvents.COMMON_SETUP.register(this::setup);
	}
	
	private void setup() {
		attributes.forEach((supplier, map) -> {
			FabricDefaultAttributeRegistry.register(supplier.get(), map);
		});
	}
	
	public static class Factory implements EntityAttributeRegister.Factory {
		
		@Override
		public EntityAttributeRegister create() {
			return new FabricEntityAttributeRegister();
		}
	}
	
}

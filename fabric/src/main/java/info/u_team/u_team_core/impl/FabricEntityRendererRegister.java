package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.client.EntityRendererRegister;
import info.u_team.u_team_core.event.SetupEvents;
import info.u_team.u_team_core.impl.common.CommonEntityRendererRegister;
import info.u_team.u_team_core.util.CastUtil;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class FabricEntityRendererRegister extends CommonEntityRendererRegister {
	
	@Override
	public void register() {
		SetupEvents.COMMON_SETUP.register(this::setup);
	}
	
	private void setup() {
		entityProviders.forEach((supplier, provider) -> {
			EntityRenderers.register(CastUtil.uncheckedCast(supplier.get()), provider);
		});
		blockEntityProviders.forEach((supplier, provider) -> {
			BlockEntityRenderers.register(CastUtil.uncheckedCast(supplier.get()), provider);
		});
	}
	
	public static class Factory implements EntityRendererRegister.Factory {
		
		@Override
		public EntityRendererRegister create() {
			return new FabricEntityRendererRegister();
		}
	}
	
}

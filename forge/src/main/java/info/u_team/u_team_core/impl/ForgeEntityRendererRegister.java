package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.client.EntityRendererRegister;
import info.u_team.u_team_core.impl.common.CommonEntityRendererRegister;
import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;

public class ForgeEntityRendererRegister extends CommonEntityRendererRegister {
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> bus.addListener(this::registerRenderers));
	}
	
	private void registerRenderers(RegisterRenderers event) {
		entityProviders.forEach((supplier, provider) -> {
			event.registerEntityRenderer(CastUtil.uncheckedCast(supplier.get()), provider);
		});
		blockEntityProviders.forEach((supplier, provider) -> {
			event.registerBlockEntityRenderer(CastUtil.uncheckedCast(supplier.get()), provider);
		});
	}
	
	public static class Factory implements EntityRendererRegister.Factory {
		
		@Override
		public EntityRendererRegister create() {
			return new ForgeEntityRendererRegister();
		}
	}
	
}

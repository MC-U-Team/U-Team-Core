package test;

import info.u_team.u_team_core.registry.ClientRegistry;

public class ClientProxyTest extends CommonProxyTest {
	
	public void init() {
		super.init();
		ClientRegistry.registerSpecialTileEntityRenderer(TileEntityTest.class, new TileEntitySpecialRenderTest());
	}
	
}

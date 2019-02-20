package info.u_team.u_team_test.proxy;

import info.u_team.u_team_test.init.TestGuis;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {
	
	@Override
	public void construct() {
		super.construct();
		TestGuis.contruct();
	}
	
	@Override
	public void setup() {
		super.setup();
	}
	
	@Override
	public void complete() {
		super.complete();
	}
	
}

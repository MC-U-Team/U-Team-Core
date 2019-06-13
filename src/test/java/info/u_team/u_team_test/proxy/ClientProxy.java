package info.u_team.u_team_test.proxy;

import info.u_team.u_team_test.init.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {
	
	@Override
	public void construct() {
		super.construct();
		TestColors.construct();
		TestGuis.construct();
	}
	
	@Override
	public void setup() {
		super.setup();
		TestModels.setup();
	}
	
	@Override
	public void complete() {
		super.complete();
	}
	
}

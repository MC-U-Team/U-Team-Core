package info.u_team.u_team_core.intern.proxy;

import info.u_team.u_team_core.api.IModProxy;
import info.u_team.u_team_core.intern.config.ClientConfig;
import info.u_team.u_team_core.intern.init.*;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class CommonProxy implements IModProxy {
	
	@Override
	public void construct() {
		ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.config);
		UCoreNetwork.construct();
	}
	
	@Override
	public void setup() {
	}
	
	@Override
	public void complete() {
	}
	
}

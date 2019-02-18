package info.u_team.u_team_core.intern.proxy;

import info.u_team.u_team_core.api.IModProxy;
import info.u_team.u_team_core.intern.config.ClientConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class CommonProxy implements IModProxy {
	
	@Override
	public void construct() {
		ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.config);
	}
	
	@Override
	public void setup() {
	}
	
	@Override
	public void complete() {
	}
	
}

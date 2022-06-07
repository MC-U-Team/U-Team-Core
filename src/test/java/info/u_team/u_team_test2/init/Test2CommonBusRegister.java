package info.u_team.u_team_test2.init;

import org.apache.logging.log4j.LogManager;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_test2.TestMod2;
import net.minecraftforge.registries.RegisterEvent;

@Construct(modid = TestMod2.MODID)
public class Test2CommonBusRegister implements ModConstruct {
	
	@Override
	public void construct() {
		BusRegister.registerMod(bus -> bus.addListener(this::registerCall));
	}
	
	private void registerCall(RegisterEvent event) {
		LogManager.getLogger().info("Hello from the register event for test mod 2");
	}
	
}

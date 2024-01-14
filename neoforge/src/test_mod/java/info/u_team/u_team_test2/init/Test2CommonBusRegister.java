package info.u_team.u_team_test2.init;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_test2.TestMod2;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.RegisterEvent;

@Construct(modid = TestMod2.MODID)
public class Test2CommonBusRegister implements ModConstruct {
	
	@Override
	public void construct() {
		BusRegister.registerMod(bus -> bus.addListener(this::registerCall));
	}
	
	private void registerCall(RegisterEvent event) {
		if (event.getRegistryKey() == Registries.BLOCK) {
			TestMod2.LOGGER.info("Hello from the register event for test mod 2");
		}
	}
	
}

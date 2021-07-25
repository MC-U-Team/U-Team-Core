package info.u_team.u_team_test2.init;

import org.apache.logging.log4j.LogManager;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.IModConstruct;
import info.u_team.u_team_test2.TestMod2;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Construct(modid = TestMod2.MODID)
public class Test2CommonBusRegister implements IModConstruct {
	
	@Override
	public void construct() {
		Bus.MOD.bus().get().addGenericListener(Block.class, this::registerCall);
	}
	
	private void registerCall(Register<Block> event) {
		LogManager.getLogger().info("Hello from the register event for test mod 2");
	}
	
}

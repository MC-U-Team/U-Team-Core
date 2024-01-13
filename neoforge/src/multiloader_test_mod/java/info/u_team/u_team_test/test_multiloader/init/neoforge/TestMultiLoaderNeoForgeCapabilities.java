package info.u_team.u_team_test.test_multiloader.init.neoforge;

import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_test.test_multiloader.blockentity.neoforge.NeoForgeTestInventoryBlockEntity;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderBlockEntityTypes;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class TestMultiLoaderNeoForgeCapabilities {
	
	private static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TestMultiLoaderBlockEntityTypes.TEST_INVENTORY.get(), (blockEntity, context) -> {
			return CastUtil.assertCast(blockEntity, NeoForgeTestInventoryBlockEntity.class).getSlots();
		});
	}
	
	static void register() {
		BusRegister.registerMod(bus -> bus.addListener(TestMultiLoaderNeoForgeCapabilities::registerCapabilities));
	}
	
}

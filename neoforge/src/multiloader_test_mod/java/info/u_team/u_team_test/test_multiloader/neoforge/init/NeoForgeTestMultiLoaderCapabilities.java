package info.u_team.u_team_test.test_multiloader.neoforge.init;

import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderBlockEntityTypes;
import info.u_team.u_team_test.test_multiloader.neoforge.blockentity.NeoForgeTestInventoryBlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class NeoForgeTestMultiLoaderCapabilities {
	
	private static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TestMultiLoaderBlockEntityTypes.TEST_INVENTORY.get(), (blockEntity, context) -> {
			return CastUtil.assertCast(blockEntity, NeoForgeTestInventoryBlockEntity.class).getSlots();
		});
	}
	
	static void register() {
		BusRegister.registerMod(bus -> bus.addListener(NeoForgeTestMultiLoaderCapabilities::registerCapabilities));
	}
	
}

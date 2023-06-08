package info.u_team.u_team_test.test_multiloader.init.fabric;

import info.u_team.u_team_core.event.SetupEvents;
import info.u_team.u_team_test.test_multiloader.blockentity.fabric.FabricTestInventoryBlockEntity;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderBlockEntityTypes;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;

public class TestMultiLoaderFabricTransactionRegister {
	
	private static void setup() {
		ItemStorage.SIDED.registerForBlockEntity((blockEntity, side) -> ((FabricTestInventoryBlockEntity) blockEntity).getSlotWrapper(), TestMultiLoaderBlockEntityTypes.TEST_INVENTORY.get());
	}
	
	static void register() {
		SetupEvents.COMMON_SETUP.register(TestMultiLoaderFabricTransactionRegister::setup);
	}
	
}

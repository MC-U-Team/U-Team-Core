package info.u_team.u_team_test.test_multiloader.fabric.init;

import info.u_team.u_team_core.event.SetupEvents;
import info.u_team.u_team_test.test_multiloader.fabric.blockentity.FabricTestInventoryBlockEntity;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderBlockEntityTypes;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;

public class FabricTestMultiLoaderTransactionRegister {
	
	private static void setup() {
		ItemStorage.SIDED.registerForBlockEntity((blockEntity, side) -> ((FabricTestInventoryBlockEntity) blockEntity).getSlotWrapper(), TestMultiLoaderBlockEntityTypes.TEST_INVENTORY.get());
	}
	
	static void register() {
		SetupEvents.COMMON_SETUP.register(FabricTestMultiLoaderTransactionRegister::setup);
	}
	
}

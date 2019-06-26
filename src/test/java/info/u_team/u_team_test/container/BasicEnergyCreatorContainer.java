package info.u_team.u_team_test.container;

import info.u_team.u_team_core.container.*;
import info.u_team.u_team_test.init.TestContainers;
import net.minecraft.entity.player.PlayerInventory;

public class BasicEnergyCreatorContainer extends USyncedTileEntityContainer<T> {

	public BasicEnergyCreatorContainer(int id, PlayerInventory playerInventory) {
		super(TestContainers.BASIC_ENERGY_CREATOR, id);
		
		tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
			for (int index = 0; index < handler.getSlots(); index++) {
				addSlot(new SlotItemHandler(handler, index, index * 18 + 80, 54));
			}
		});
		
		appendPlayerInventory(playerInventory, 8, 100);
	}

}

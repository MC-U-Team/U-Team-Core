package info.u_team.u_team_test.container;

import info.u_team.u_team_core.menu.UBlockEntityContainerMenu;
import info.u_team.u_team_test.init.TestContainers;
import info.u_team.u_team_test.tileentity.BasicFluidInventoryTileEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class BasicFluidInventoryContainer extends UBlockEntityContainerMenu<BasicFluidInventoryTileEntity> {
	
	// Client
	public BasicFluidInventoryContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
		super(TestContainers.BASIC_FLUID_INVENTORY.get(), id, playerInventory, buffer);
	}
	
	// Server
	public BasicFluidInventoryContainer(int id, Inventory playerInventory, BasicFluidInventoryTileEntity tileEntity) {
		super(TestContainers.BASIC_FLUID_INVENTORY.get(), id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
		appendFluidInventory(tileEntity.getFluidTanks(), 1, 4, 8, 18);
		appendInventory(tileEntity.getItemSlots(), 1, 4, 8, 50);
		appendPlayerInventory(playerInventory, 8, 82);
	}
	
}

package info.u_team.u_team_test.menu;

import info.u_team.u_team_core.menu.UBlockEntityContainerMenu;
import info.u_team.u_team_test.blockentity.BasicFluidInventoryBlockEntity;
import info.u_team.u_team_test.init.TestMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.fml.LogicalSide;

public class BasicFluidInventoryMenu extends UBlockEntityContainerMenu<BasicFluidInventoryBlockEntity> {
	
	// Client
	public BasicFluidInventoryMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buffer) {
		super(TestMenuTypes.BASIC_FLUID_INVENTORY.get(), containerId, playerInventory, buffer);
	}
	
	// Server
	public BasicFluidInventoryMenu(int containerId, Inventory playerInventory, BasicFluidInventoryBlockEntity tileEntity) {
		super(TestMenuTypes.BASIC_FLUID_INVENTORY.get(), containerId, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(LogicalSide side) {
		addFluidSlots(blockEntity.getFluidTanks(), 1, 4, 8, 18);
		addSlots(blockEntity.getItemSlots(), 1, 4, 8, 50);
		addPlayerInventory(playerInventory, 8, 82);
		
		fluidSlots.get(0).setBackground(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
		slots.get(0).setBackground(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
	}
	
}

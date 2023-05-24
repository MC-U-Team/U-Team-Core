package info.u_team.u_team_test.menu;

import info.u_team.u_team_core.menu.ForgeFluidContainerMenuDelegator;
import info.u_team.u_team_core.menu.ItemHandlerSlotCreator;
import info.u_team.u_team_core.menu.UBlockEntityContainerMenu;
import info.u_team.u_team_test.blockentity.BasicFluidInventoryBlockEntity;
import info.u_team.u_team_test.init.TestMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;

public class BasicFluidInventoryMenu extends UBlockEntityContainerMenu<BasicFluidInventoryBlockEntity> {
	
	// Client
	public BasicFluidInventoryMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buffer) {
		super(TestMenuTypes.BASIC_FLUID_INVENTORY.get(), containerId, playerInventory, buffer);
	}
	
	// Server
	public BasicFluidInventoryMenu(int containerId, Inventory playerInventory, BasicFluidInventoryBlockEntity blockEntity) {
		super(TestMenuTypes.BASIC_FLUID_INVENTORY.get(), containerId, playerInventory, blockEntity);
	}
	
	@Override
	protected void init(LogicalSide side) {
		((ForgeFluidContainerMenuDelegator) getDelegator()).addFluidSlots(blockEntity.getFluidTanks(), 1, 4, 8, 18);
		addSlots(ItemHandlerSlotCreator.of(blockEntity.getItemSlots()), 1, 4, 8, 50);
		addPlayerInventory(playerInventory, 8, 82);
		
		((ForgeFluidContainerMenuDelegator) getDelegator()).fluidSlots.get(0).setBackground(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
		slots.get(0).setBackground(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
	}
	
	@Override
	public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
		return null;
	}
	
}

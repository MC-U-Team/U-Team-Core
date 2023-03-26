package info.u_team.u_team_test.menu;

import info.u_team.u_team_core.menu.UBlockEntityContainerMenu;
import info.u_team.u_team_test.blockentity.BasicEnergyCreatorBlockEntity;
import info.u_team.u_team_test.init.TestMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;

public class BasicEnergyCreatorMenu extends UBlockEntityContainerMenu<BasicEnergyCreatorBlockEntity> {
	
	public BasicEnergyCreatorMenu(int containerId, Inventory playerInventory, FriendlyByteBuf byteBuf) {
		super(TestMenuTypes.BASIC_ENERGY_CREATOR.get(), containerId, playerInventory, byteBuf);
	}
	
	public BasicEnergyCreatorMenu(int containerId, Inventory playerInventory, BasicEnergyCreatorBlockEntity blockEntity) {
		super(TestMenuTypes.BASIC_ENERGY_CREATOR.get(), containerId, playerInventory, blockEntity);
	}
	
	@Override
	protected void init(LogicalSide side) {
		addSlots(blockEntity.getSlots(), 2, 3, 116, 41);
		addPlayerInventory(playerInventory, 8, 91);
		addDataHolderToClient(blockEntity.getEnergy().createSyncHandler());
	}
	
	@Override
	public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
		return null;
	}
}

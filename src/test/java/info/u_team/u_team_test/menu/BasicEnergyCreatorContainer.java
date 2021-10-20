package info.u_team.u_team_test.menu;

import info.u_team.u_team_core.menu.UBlockEntityContainerMenu;
import info.u_team.u_team_test.blockentity.BasicEnergyCreatorBlockEntity;
import info.u_team.u_team_test.init.TestMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fml.LogicalSide;

public class BasicEnergyCreatorContainer extends UBlockEntityContainerMenu<BasicEnergyCreatorBlockEntity> {
	
	public BasicEnergyCreatorContainer(int containerId, Inventory playerInventory, FriendlyByteBuf byteBuf) {
		super(TestMenuTypes.BASIC_ENERGY_CREATOR.get(), containerId, playerInventory, byteBuf);
	}
	
	public BasicEnergyCreatorContainer(int containerId, Inventory playerInventory, BasicEnergyCreatorBlockEntity tileEntity) {
		super(TestMenuTypes.BASIC_ENERGY_CREATOR.get(), containerId, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(LogicalSide side) {
		addSlots(blockEntity.getSlots(), 2, 3, 116, 41);
		addPlayerInventory(playerInventory, 8, 91);
		addDataHolderToClient(blockEntity.getEnergy().createSyncHandler());
	}
}

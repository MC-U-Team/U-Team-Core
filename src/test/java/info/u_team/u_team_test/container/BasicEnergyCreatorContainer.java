package info.u_team.u_team_test.container;

import info.u_team.u_team_core.menu.UBlockEntityContainerMenu;
import info.u_team.u_team_test.init.TestContainers;
import info.u_team.u_team_test.tileentity.BasicEnergyCreatorTileEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class BasicEnergyCreatorContainer extends UBlockEntityContainerMenu<BasicEnergyCreatorTileEntity> {
	
	public BasicEnergyCreatorContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
		super(TestContainers.BASIC_ENERGY_CREATOR.get(), id, playerInventory, buffer);
	}
	
	public BasicEnergyCreatorContainer(int id, Inventory playerInventory, BasicEnergyCreatorTileEntity tileEntity) {
		super(TestContainers.BASIC_ENERGY_CREATOR.get(), id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
		addSlots(tileEntity.getSlots(), 2, 3, 116, 41);
		addPlayerInventory(playerInventory, 8, 91);
		addDataHolderToClient(tileEntity.getEnergy().createSyncHandler());
	}
}

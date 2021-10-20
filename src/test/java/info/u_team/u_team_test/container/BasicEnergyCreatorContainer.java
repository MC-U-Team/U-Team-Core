package info.u_team.u_team_test.container;

import info.u_team.u_team_core.menu.UBlockEntityContainerMenu;
import info.u_team.u_team_test.blockentity.BasicEnergyCreatorTileEntity;
import info.u_team.u_team_test.init.TestMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fml.LogicalSide;

public class BasicEnergyCreatorContainer extends UBlockEntityContainerMenu<BasicEnergyCreatorTileEntity> {
	
	public BasicEnergyCreatorContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
		super(TestMenuTypes.BASIC_ENERGY_CREATOR.get(), id, playerInventory, buffer);
	}
	
	public BasicEnergyCreatorContainer(int id, Inventory playerInventory, BasicEnergyCreatorTileEntity tileEntity) {
		super(TestMenuTypes.BASIC_ENERGY_CREATOR.get(), id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(LogicalSide side) {
		addSlots(blockEntity.getSlots(), 2, 3, 116, 41);
		addPlayerInventory(playerInventory, 8, 91);
		addDataHolderToClient(blockEntity.getEnergy().createSyncHandler());
	}
}

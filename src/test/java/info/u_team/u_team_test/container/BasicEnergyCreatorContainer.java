package info.u_team.u_team_test.container;

import info.u_team.u_team_core.container.USyncedTileEntityContainer;
import info.u_team.u_team_core.energy.BasicEnergyStorage;
import info.u_team.u_team_test.init.TestContainers;
import info.u_team.u_team_test.tileentity.BasicEnergyCreatorTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IntReferenceHolder;
import net.minecraftforge.items.CapabilityItemHandler;

public class BasicEnergyCreatorContainer extends USyncedTileEntityContainer<BasicEnergyCreatorTileEntity> {
	
	public BasicEnergyCreatorContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(TestContainers.BASIC_ENERGY_CREATOR, id, playerInventory, buffer);
	}
	
	public BasicEnergyCreatorContainer(int id, PlayerInventory playerInventory, BasicEnergyCreatorTileEntity tileEntity) {
		super(TestContainers.BASIC_ENERGY_CREATOR, id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
		tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> appendInventory(handler, 2, 3, 20, 20));
		appendPlayerInventory(playerInventory, 8, 100);
		
		func_216958_a(new IntReferenceHolder() {
			
			@Override
			public int get() {
				return BasicEnergyStorage.getTileEntityEnergy(tileEntity);
			}
			
			@Override
			public void set(int value) {
				BasicEnergyStorage.setTileEntityEnergy(tileEntity, value);
			}
		});
	}
}

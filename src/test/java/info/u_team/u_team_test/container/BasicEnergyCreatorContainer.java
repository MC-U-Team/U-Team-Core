package info.u_team.u_team_test.container;

import info.u_team.u_team_core.container.UContainer;
import info.u_team.u_team_test.init.TestContainers;
import net.minecraft.entity.player.PlayerInventory;

public class BasicEnergyCreatorContainer extends UContainer {

	public BasicEnergyCreatorContainer(int id, PlayerInventory playerInventory) {
		super(TestContainers.BASIC_ENERGY_CREATOR, id);
		
		appendPlayerInventory(playerInventory, 8, 100);
	}

}

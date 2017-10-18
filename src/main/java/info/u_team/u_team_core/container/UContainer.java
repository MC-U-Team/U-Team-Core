package info.u_team.u_team_core.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class UContainer extends Container {
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return false;
	}
	
}

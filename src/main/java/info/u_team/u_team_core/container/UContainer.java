package info.u_team.u_team_core.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * Container API<br>
 * -> Basic Container
 * 
 * @date 21.10.2017
 * @author HyCraftHD
 */
public class UContainer extends Container {
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
}

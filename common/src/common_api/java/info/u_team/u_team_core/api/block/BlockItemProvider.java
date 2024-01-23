package info.u_team.u_team_core.api.block;

import info.u_team.u_team_core.api.registry.BlockRegister;
import net.minecraft.world.item.Item;

/**
 * Implement this in a block to get the block item automatically registered with {@link BlockRegister}
 *
 * @author HyCraftHD
 */
public interface BlockItemProvider {
	
	/**
	 * Must return the same block item instance every time you call this method. The registry name will be set to the name
	 * from the block. Can return null for no block item. If this method is called the block item must be registered!
	 *
	 * @return Block item instance
	 */
	Item blockItem();
	
}

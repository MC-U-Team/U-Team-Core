package info.u_team.u_team_core.api.registry;

import info.u_team.u_team_core.util.registry.BlockDeferredRegister;
import net.minecraft.world.item.BlockItem;

/**
 * Implement this to a block to get the block item automatically registered with {@link BlockDeferredRegister}
 *
 * @author HyCraftHD
 */
public interface IBlockItemProvider {

	/**
	 * Must return the same block item instance every time you call this method. The registry name will be set to the name
	 * from the block. Can return null for no block item.
	 *
	 * @return Block item instance
	 */
	BlockItem getBlockItem();

}

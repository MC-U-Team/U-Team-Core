package info.u_team.u_team_core.api.registry;

import net.minecraft.item.BlockItem;

/**
 * 
 */
public interface IBlockItemProvider {
	
	/**
	 * Must return the same block item instance every time you call this method. The registry name will be set to the name
	 * from the block. Can return null for no block item.
	 * 
	 * @return Block item instance
	 */
	public BlockItem getBlockItem();
	
}

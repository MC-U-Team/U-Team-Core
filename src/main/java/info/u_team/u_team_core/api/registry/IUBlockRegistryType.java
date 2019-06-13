package info.u_team.u_team_core.api.registry;

import net.minecraft.item.BlockItem;

/**
 * This can be implemented in blocks to give register them easier.
 * 
 * @see IURegistryType
 */
public interface IUBlockRegistryType extends IURegistryType {
	
	/**
	 * Must return the same block item instance every time you call this method. The registry name will be set to the name
	 * from the block.
	 * 
	 * @return Block item instance
	 */
	public BlockItem getBlockItem();
}

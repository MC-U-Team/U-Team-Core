package info.u_team.u_team_core.api.registry;

import info.u_team.u_team_core.item.UItemBlock;
import net.minecraft.item.Item;

/**
 * Mark a block as ublock for registry things
 * 
 * @author HyCraftHD
 * @date 24.06.2018
 */
public interface IUBlock extends IURegistry {
	
	public Item getItem();
	
	public UItemBlock getItemBlock();
	
}

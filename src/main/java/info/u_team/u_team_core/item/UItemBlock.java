package info.u_team.u_team_core.item;

import javax.annotation.Nonnull;

import info.u_team.u_team_core.blocks.UBlock;
import net.minecraft.item.ItemBlock;

/**
 * ItemBlock API<br>
 * -> Basic ItemBlock
 * 
 * @date 17.08.2017
 * @author MrTroble
 */

public class UItemBlock extends ItemBlock {
	
	public UItemBlock(@Nonnull UBlock block) {
		super(block);
		this.setRegistryName(block.getRegistryName());
	}
	
}

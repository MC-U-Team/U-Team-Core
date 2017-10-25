package info.u_team.u_team_core.item;

import info.u_team.u_team_core.block.UBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

/**
 * ItemBlock API<br>
 * -> Basic ItemBlock
 * 
 * @date 17.08.2017
 * @author MrTroble
 */

public class UItemBlock extends ItemBlock {
	
	public UItemBlock(Block block) {
		super(block);
		if (!(block instanceof UBlock)) {
			throw new IllegalArgumentException("You need to use an instance of UBlock here.");
		}
		this.setUnlocalizedName(block.getUnlocalizedName());
	}
	
}

package info.u_team.u_team_core.item;

import info.u_team.u_team_core.UCoreMain;
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
	
	public UItemBlock(UBlock block) {
		super(block);
		this.setRegistryName(UCoreMain.SUBMOD_MODID, "item" + block.getRegistryName());
	}
	
}

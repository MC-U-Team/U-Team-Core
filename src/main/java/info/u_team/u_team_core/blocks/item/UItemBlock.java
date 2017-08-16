package info.u_team.u_team_core.blocks.item;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.blocks.UBlock;
import net.minecraft.item.ItemBlock;

/**
 * ItemBlock API<br>
 *   -> Basic ItemBlock
 * 
 * @author MrTroble
 */

public class UItemBlock extends ItemBlock{

	public UItemBlock(UBlock block) {
		super(block);
		this.setRegistryName(UCoreMain.SUBMOD_MODID, "item" + block.getRegistryName());
	}

}

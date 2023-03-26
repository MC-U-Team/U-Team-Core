package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UBlock;
import net.minecraft.world.level.material.Material;

public class BasicNoItemBlock extends UBlock {
	
	public BasicNoItemBlock() {
		super(Properties.of(Material.STONE).noLootTable().instabreak());
	}
	
}

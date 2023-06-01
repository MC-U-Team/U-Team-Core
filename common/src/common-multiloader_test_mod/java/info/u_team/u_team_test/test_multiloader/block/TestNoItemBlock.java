package info.u_team.u_team_test.test_multiloader.block;

import info.u_team.u_team_core.block.UBlock;
import net.minecraft.world.level.material.Material;

public class TestNoItemBlock extends UBlock {
	
	public TestNoItemBlock() {
		super(Properties.of(Material.STONE).noLootTable().instabreak());
	}
	
}

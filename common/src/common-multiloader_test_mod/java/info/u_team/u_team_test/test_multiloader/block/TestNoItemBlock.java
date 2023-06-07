package info.u_team.u_team_test.test_multiloader.block;

import info.u_team.u_team_core.block.UBlock;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class TestNoItemBlock extends UBlock {
	
	public TestNoItemBlock() {
		super(Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).noLootTable().instabreak());
	}
	
}

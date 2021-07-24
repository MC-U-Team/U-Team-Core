package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_test.init.TestItemGroups;

public class BasicBlock extends UBlock {
	
	public BasicBlock() {
		super(TestItemGroups.GROUP, Properties.create(Material.ROCK).hardnessAndResistance(2F).sound(SoundType.GROUND).slipperiness(0.8F).setLightLevel(state -> 1), new Item.Properties().rarity(Rarity.UNCOMMON));
	}
	
}

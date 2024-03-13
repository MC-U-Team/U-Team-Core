package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_test.init.TestItemGroups;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;

public class BasicBlock extends UBlock {
	
	public BasicBlock() {
		super(TestItemGroups.GROUP, Properties.create(Material.ROCK).hardnessAndResistance(2F).sound(SoundType.GROUND).slipperiness(0.8F).lightValue(1), new Item.Properties().rarity(Rarity.UNCOMMON));
	}
	
}

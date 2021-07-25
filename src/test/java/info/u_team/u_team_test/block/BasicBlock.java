package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_test.init.TestItemGroups;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class BasicBlock extends UBlock {
	
	public BasicBlock() {
		super(TestItemGroups.GROUP, Properties.of(Material.STONE).strength(2F).sound(SoundType.GRAVEL).friction(0.8F).lightLevel(state -> 1), new Item.Properties().rarity(Rarity.UNCOMMON));
	}
	
}

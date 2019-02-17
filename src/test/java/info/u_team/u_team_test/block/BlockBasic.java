package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_test.init.TestItemGroups;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;

public class BlockBasic extends UBlock {
	
	public BlockBasic(String name) {
		super(name, TestItemGroups.group, Properties.create(Material.ROCK).hardnessAndResistance(2F).sound(SoundType.GROUND).slipperiness(0.8F).lightValue(1));
	}
	
	@Override
	protected ItemBlock createItemBlock(ItemGroup group) {
		return new ItemBlock(this, new Item.Properties().rarity(EnumRarity.UNCOMMON).group(group));
	}
	
}

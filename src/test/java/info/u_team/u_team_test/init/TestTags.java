package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.TagUtil;
import info.u_team.u_team_test.TestMod;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class TestTags {
	
	public static class Blocks {
		
		public static Named<Block> TEST_TAG_1 = TagUtil.createBlockTag(TestMod.MODID, "test_1");
		public static Named<Block> TEST_TAG_2 = TagUtil.createBlockTag(TestMod.MODID, "test_2");
		
		public static Named<Block> TEST_TAG_2_SAME = TagUtil.createBlockTag(TestMod.MODID, "test_2");
		public static IOptionalNamedTag<Block> TEST_TAG_2_OPTIONAL = TagUtil.createOptionalBlockTag(TestMod.MODID, "test_2");
	}
	
	public static class Items {
		
		public static Named<Item> TEST_TAG_1 = TagUtil.fromBlockTag(Blocks.TEST_TAG_1);
		public static Named<Item> TEST_TAG_2 = TagUtil.fromBlockTag(Blocks.TEST_TAG_2);
		public static Named<Item> TEST_TAG_3 = TagUtil.createItemTag(TestMod.MODID, "test_3");
		public static Named<Item> TEST_TAG_4 = TagUtil.createItemTag(TestMod.MODID, "test_4");
	}
	
}

package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.TagUtil;
import info.u_team.u_team_test.TestMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag.INamedTag;

public class TestTags {
	
	public static class Blocks {
		
		public static INamedTag<Block> TEST_TAG_1 = TagUtil.createBlockTag(TestMod.MODID, "test_1");
		public static INamedTag<Block> TEST_TAG_2 = TagUtil.createBlockTag(TestMod.MODID, "test_2");
	}
	
	public static class Items {
		
		public static INamedTag<Item> TEST_TAG_1 = TagUtil.fromBlockTag(Blocks.TEST_TAG_1);
		public static INamedTag<Item> TEST_TAG_2 = TagUtil.fromBlockTag(Blocks.TEST_TAG_2);
		public static INamedTag<Item> TEST_TAG_3 = TagUtil.createItemTag(TestMod.MODID, "test_3");
	}
	
}

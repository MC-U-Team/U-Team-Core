//package info.u_team.u_team_test.data.provider;
//
//import java.util.stream.IntStream;
//
//import info.u_team.u_team_core.data.GenerationData;
//import info.u_team.u_team_test.init.TestTags;
//import net.minecraft.core.Registry;
//import net.minecraft.data.tags.TagsProvider;
//import net.minecraft.tags.ItemTags;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.Items;
//import net.minecraftforge.common.Tags;
//import net.minecraftforge.registries.ForgeRegistries;
//
//public class TestItemTagsProvider extends TagsProvider<Item> {
//	
//	@SuppressWarnings("deprecation")
//	public TestItemTagsProvider(GenerationData data) {
//		super(data.getGenerator(), Registry.ITEM, data.getModid(), data.getExistingFileHelper());
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	protected void addTags() {
//		// copy(TestTags.Blocks.TEST_TAG_1, TestTags.Items.TEST_TAG_1); // TODO Fix how?
//		// copy(TestTags.Blocks.TEST_TAG_2, TestTags.Items.TEST_TAG_2);
//		
//		tag(TestTags.Items.TEST_TAG_3).add(Items.BEACON).addTag(TestTags.Items.TEST_TAG_2).add(Items.BARREL).add(Items.BEACON, Items.ACACIA_BUTTON);
//		
//		IntStream.range(0, 10).forEach(index -> {
//			tag(TestTags.Items.TEST_TAG_3).add(Items.BEACON).addTag(TestTags.Items.TEST_TAG_2).add(Item.byId(index + 1));
//		});
//		
//		tag(TestTags.Items.TEST_TAG_4) //
//				.add(Items.BLACKSTONE) //
//				.addTag(ItemTags.LOGS) //
//				.add(Items.FURNACE, Items.SMOKER, Items.BLAST_FURNACE) //
//				.addTags(Tags.Items.CHESTS_TRAPPED, Tags.Items.CHESTS_WOODEN) //
//				.addOptional(ForgeRegistries.ITEMS.getKey(Items.ANCIENT_DEBRIS)) //
//				.addOptionalTag(ItemTags.ANVIL.location());
//	}
//	
//}

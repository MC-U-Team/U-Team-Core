package info.u_team.u_team_test.data.provider;

import java.util.stream.IntStream;

import info.u_team.u_team_core.data.CommonItemTagsProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_core.util.RegistryUtil;
import info.u_team.u_team_test.init.TestItems;
import info.u_team.u_team_test.init.TestTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

public class TestItemTagsProvider extends CommonItemTagsProvider {
	
	public TestItemTagsProvider(GenerationData generationData, TestBlockTagsProvider blockTagsProvider) {
		super(generationData, blockTagsProvider);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void register(HolderLookup.Provider provider) {
		tag(ItemTags.HEAD_ARMOR).add(TestItems.BASIC_ARMOR.helmet().get());
		tag(ItemTags.CHEST_ARMOR).add(TestItems.BASIC_ARMOR.chestplate().get());
		tag(ItemTags.LEG_ARMOR).add(TestItems.BASIC_ARMOR.leggings().get());
		tag(ItemTags.FOOT_ARMOR).add(TestItems.BASIC_ARMOR.boots().get());
		
		tag(ItemTags.AXES).add(TestItems.BASIC_TOOL.axe().get());
		tag(ItemTags.HOES).add(TestItems.BASIC_TOOL.hoe().get());
		tag(ItemTags.PICKAXES).add(TestItems.BASIC_TOOL.pickaxe().get());
		tag(ItemTags.SHOVELS).add(TestItems.BASIC_TOOL.shovel().get());
		tag(ItemTags.SWORDS).add(TestItems.BASIC_TOOL.sword().get());
		
		tag(ItemTags.CLUSTER_MAX_HARVESTABLES).add(TestItems.BASIC_TOOL.pickaxe().get());
		
		copy(TestTags.Blocks.TEST_TAG_1, TestTags.Items.TEST_TAG_1);
		copy(TestTags.Blocks.TEST_TAG_2, TestTags.Items.TEST_TAG_2);
		
		tag(TestTags.Items.TEST_TAG_3).add(Items.BEACON).addTag(TestTags.Items.TEST_TAG_2).add(Items.BARREL).add(Items.BEACON, Items.ACACIA_BUTTON);
		
		IntStream.range(0, 10).forEach(index -> {
			tag(TestTags.Items.TEST_TAG_3).add(Items.BEACON).addTag(TestTags.Items.TEST_TAG_2).add(Item.byId(index + 1));
		});
		
		tag(TestTags.Items.TEST_TAG_4) //
				.add(Items.BLACKSTONE) //
				.addTag(ItemTags.LOGS) //
				.add(Items.FURNACE, Items.SMOKER, Items.BLAST_FURNACE) //
				.addTags(Tags.Items.CHESTS_TRAPPED, Tags.Items.CHESTS_WOODEN) //
				.addOptional(RegistryUtil.getBuiltInRegistry(Registries.ITEM).getKey(Items.ANCIENT_DEBRIS)) //
				.addOptionalTag(ItemTags.ANVIL.location());
	}
	
}

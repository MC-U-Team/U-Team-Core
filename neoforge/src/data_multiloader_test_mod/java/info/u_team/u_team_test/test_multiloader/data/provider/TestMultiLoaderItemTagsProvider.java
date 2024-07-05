package info.u_team.u_team_test.test_multiloader.data.provider;

import info.u_team.u_team_core.data.CommonBlockTagsProvider;
import info.u_team.u_team_core.data.CommonItemTagsProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

public class TestMultiLoaderItemTagsProvider extends CommonItemTagsProvider {
	
	public TestMultiLoaderItemTagsProvider(GenerationData generationData, CommonBlockTagsProvider blockTags) {
		super(generationData, blockTags);
	}
	
	@Override
	public void register(HolderLookup.Provider registries) {
		tag(ItemTags.HEAD_ARMOR).add(TestMultiLoaderItems.TEST_ARMOR.helmet().get());
		tag(ItemTags.CHEST_ARMOR).add(TestMultiLoaderItems.TEST_ARMOR.chestplate().get());
		tag(ItemTags.LEG_ARMOR).add(TestMultiLoaderItems.TEST_ARMOR.leggings().get());
		tag(ItemTags.FOOT_ARMOR).add(TestMultiLoaderItems.TEST_ARMOR.boots().get());
		
		tag(ItemTags.AXES).add(TestMultiLoaderItems.TEST_TIER.axe().get());
		tag(ItemTags.HOES).add(TestMultiLoaderItems.TEST_TIER.hoe().get());
		tag(ItemTags.PICKAXES).add(TestMultiLoaderItems.TEST_TIER.pickaxe().get());
		tag(ItemTags.SHOVELS).add(TestMultiLoaderItems.TEST_TIER.shovel().get());
		tag(ItemTags.SWORDS).add(TestMultiLoaderItems.TEST_TIER.sword().get());
		
		tag(ItemTags.CLUSTER_MAX_HARVESTABLES).add(TestMultiLoaderItems.TEST_TIER.pickaxe().get());
	}
	
}

package info.u_team.u_team_test.data.provider;

import java.util.stream.IntStream;

import info.u_team.u_team_core.data.*;
import info.u_team.u_team_test.init.TestTags;
import net.minecraft.item.*;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

public class TestItemTagsProvider extends CommonItemTagsProvider {
	
	public TestItemTagsProvider(GenerationData data, CommonBlockTagsProvider blockProvider) {
		super(data, blockProvider);
	}
	
	@Override
	protected void registerTags() {
		copy(TestTags.Blocks.TEST_TAG_1, TestTags.Items.TEST_TAG_1);
		copy(TestTags.Blocks.TEST_TAG_2, TestTags.Items.TEST_TAG_2);
		
		getBuilder(TestTags.Items.TEST_TAG_3).add(Items.BEACON).add(TestTags.Items.TEST_TAG_2).add(Items.BARREL).add(Items.BEACON, Items.ACACIA_BUTTON);
		
		IntStream.range(0, 10).forEach(index -> {
			getBuilder(TestTags.Items.TEST_TAG_3).add(Items.BEACON).add(TestTags.Items.TEST_TAG_2).add(Item.getItemById(index + 1));
		});
		
		getBuilder(TestTags.Items.TEST_TAG_4) //
				.add(Items.BLACKSTONE) //
				.add(ItemTags.LOGS) //
				.add(Items.FURNACE, Items.SMOKER, Items.BLAST_FURNACE) //
				.add(Tags.Items.CHESTS_TRAPPED, Tags.Items.CHESTS_WOODEN) //
				.addOptional(Items.ANCIENT_DEBRIS.getRegistryName()) //
				.addOptionalTag(ItemTags.ANVIL.getName());
	}
	
}

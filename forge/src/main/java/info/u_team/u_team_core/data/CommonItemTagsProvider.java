package info.u_team.u_team_core.data;

import java.util.function.Function;

import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CommonItemTagsProvider extends CommonTagsProvider<Item> {
	
	private final Function<TagKey<Block>, TagBuilder> blockTagFunction;
	
	@SuppressWarnings("deprecation")
	public CommonItemTagsProvider(GenerationData generationData, CommonBlockTagsProvider blockTagsProvider) {
		super(generationData, ForgeRegistries.Keys.ITEMS, item -> item.builtInRegistryHolder().key());
		blockTagFunction = blockTagsProvider::getOrCreateRawBuilder;
	}
	
	@Override
	public String getName() {
		return "Item-Tags: " + nameSuffix();
	}
	
	protected void copy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
		final TagBuilder itemBuilder = getOrCreateRawBuilder(itemTag);
		final TagBuilder blockBuilder = blockTagFunction.apply(blockTag);
		blockBuilder.build().forEach(itemBuilder::add);
	}
	
}

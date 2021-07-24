package info.u_team.u_team_core.data;

import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public abstract class CommonItemTagsProvider extends CommonTagsProvider<Item> {
	
	private final Function<Tag.Named<Block>, Tag.Builder> blockTagBuilderFunction;
	
	public CommonItemTagsProvider(GenerationData data) {
		this(data, new CommonBlockTagsProvider(data) {
			
			@Override
			protected void registerTags() {
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	public CommonItemTagsProvider(GenerationData data, CommonBlockTagsProvider blockProvider) {
		super(data, Registry.ITEM);
		this.blockTagBuilderFunction = blockProvider::getTagBuilder;
	}
	
	@Override
	protected String getTagFolder() {
		return "items";
	}
	
	@Override
	public String getName() {
		return "Item-Tags";
	}
	
	protected void copy(Tag.Named<Block> blockTag, Tag.Named<Item> itemTag) {
		final Tag.Builder itemTagBuilder = getTagBuilder(itemTag);
		final Tag.Builder blockTagBuilder = blockTagBuilderFunction.apply(blockTag);
		blockTagBuilder.getEntries().forEach(itemTagBuilder::add);
	}
}

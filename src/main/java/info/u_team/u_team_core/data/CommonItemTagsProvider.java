package info.u_team.u_team_core.data;

import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.util.registry.Registry;

public abstract class CommonItemTagsProvider extends CommonTagsProvider<Item> {
	
	private final Function<ITag.INamedTag<Block>, ITag.Builder> blockTagBuilderFunction;
	
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
	
	protected void copy(ITag.INamedTag<Block> blockTag, ITag.INamedTag<Item> itemTag) {
		final ITag.Builder itemTagBuilder = getTagBuilder(itemTag);
		final ITag.Builder blockTagBuilder = blockTagBuilderFunction.apply(blockTag);
		blockTagBuilder.getProxyStream().forEach(itemTagBuilder::addProxyTag);
	}
}
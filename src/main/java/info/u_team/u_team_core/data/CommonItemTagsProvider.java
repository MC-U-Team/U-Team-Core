package info.u_team.u_team_core.data;

import java.nio.file.Path;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.tags.*;
import net.minecraft.tags.Tag.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CommonItemTagsProvider extends CommonTagsProvider<Item> {
	
	protected CommonItemTagsProvider(GenerationData data) {
		super(data, ForgeRegistries.ITEMS);
	}
	
	@Override
	protected Path makePath(ResourceLocation location) {
		return resolveData(location).resolve("tags").resolve("items").resolve(location.getPath() + ".json");
	}
	
	@Override
	protected void setCollection(TagCollection<Item> collection) {
		ItemTags.setCollection(collection);
	}
	
	@Override
	public String getName() {
		return "Item-Tags";
	}
	
	protected void copy(Tag<Block> from, Tag<Item> to) {
		from.getEntries().forEach(entry -> getBuilder(to).add(copyEntry(entry)));
	}
	
	private ITagEntry<Item> copyEntry(ITagEntry<Block> entry) {
		if (entry instanceof TagEntry) {
			return new TagEntry<>(((TagEntry<Block>) entry).getSerializedId());
		} else if (entry instanceof ListEntry) {
			final List<Item> list = Lists.newArrayList();
			
			for (final Block block : ((ListEntry<Block>) entry).getTaggedItems()) {
				final Item item = block.asItem();
				if (item == Items.AIR) {
					LOGGER.warn("Itemless block copied to item tag: {}", ForgeRegistries.BLOCKS.getKey(block));
				} else {
					list.add(item);
				}
			}
			
			return new ListEntry<>(list);
		} else {
			throw new UnsupportedOperationException("Unknown tag entry " + entry);
		}
	}
}

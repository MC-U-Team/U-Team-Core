package info.u_team.u_team_core.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;

import net.minecraft.data.DirectoryCache;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.*;

public abstract class CommonTagsProvider<T extends IForgeRegistryEntry<T>> extends CommonProvider {
	
	protected final IForgeRegistry<T> registry;
	protected final Map<Tag<T>, Tag.Builder<T>> tagToBuilder = Maps.newLinkedHashMap();
	
	protected CommonTagsProvider(GenerationData data, IForgeRegistry<T> registry) {
		super(data);
		this.registry = registry;
	}
	
	protected abstract void registerTags();
	
	@Override
	public void act(DirectoryCache cache) {
		tagToBuilder.clear();
		registerTags();
		
		final TagCollection<T> collection = new TagCollection<>(id -> Optional.empty(), "", false, "generated");
		final Map<ResourceLocation, Tag.Builder<T>> map = tagToBuilder.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().getId(), Entry::getValue));
		
		collection.registerAll(map);
		collection.getTagMap().forEach((location, tag) -> {
			final JsonObject object = tag.serialize(registry::getKey);
			final Path path = makePath(location);
			try {
				write(cache, object, path);
			} catch (IOException ex) {
				LOGGER.error(marker, "Could not write data.", ex);
			}
			
		});
		setCollection(collection);
	}
	
	protected abstract void setCollection(TagCollection<T> collection);
	
	protected abstract Path makePath(ResourceLocation location);
	
	protected Tag.Builder<T> getBuilder(Tag<T> tag) {
		return tagToBuilder.computeIfAbsent(tag, otherTag -> Tag.Builder.create());
	}
}
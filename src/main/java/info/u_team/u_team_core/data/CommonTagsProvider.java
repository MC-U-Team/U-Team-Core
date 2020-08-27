package info.u_team.u_team_core.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;

import net.minecraft.data.*;
import net.minecraft.data.TagsProvider.Builder;
import net.minecraft.tags.*;
import net.minecraft.tags.ITag.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public abstract class CommonTagsProvider<T> extends CommonProvider {
	
	protected final Registry<T> registry;
	protected final Map<ResourceLocation, ITag.Builder> tagToBuilder = Maps.newLinkedHashMap();
	
	public CommonTagsProvider(GenerationData data, Registry<T> registry) {
		super(data);
		this.registry = registry;
	}
	
	protected abstract void registerTags();
	
	@Override
	public void act(DirectoryCache cache) {
		tagToBuilder.clear();
		registerTags();
		
		tagToBuilder.forEach((location, builder) -> {
			final List<ITag.Proxy> list = builder.func_232963_b_(id -> Tag.func_241284_a_(), id -> registry.func_241873_b(id).orElse(null)).collect(Collectors.toList());
			if (!list.isEmpty()) {
				throw new IllegalArgumentException(String.format("Couldn't define tag %s as it is missing following references: %s", location, list.stream().map(Objects::toString).collect(Collectors.joining(","))));
			}
			final JsonObject object = builder.serialize();
			final Path path = makePath(location);
			try {
				write(cache, object, path);
			} catch (final IOException ex) {
				LOGGER.error(marker, "Could not write data.", ex);
			}
		});
	}
	
	protected abstract Path makePath(ResourceLocation location);
	
	protected TagsProvider.Builder<T> getBuilder(ITag.INamedTag<T> tag) {
		final ITag.Builder tagBuilder = getTagBuilder(tag);
		return new UniqueBuilder<>(tagBuilder, registry, modid);
	}
	
	protected ITag.Builder getTagBuilder(ITag.INamedTag<T> tag) {
		return this.tagToBuilder.computeIfAbsent(tag.getName(), location -> new ITag.Builder());
	}
	
	public static class UniqueBuilder<T> extends TagsProvider.Builder<T> {
		
		private final Registry<T> registry;
		
		public UniqueBuilder(ITag.Builder builder, Registry<T> registry, String id) {
			super(builder, registry, id);
			this.registry = registry;
		}
		
		@Override
		public Builder<T> addItemEntry(T item) {
			final ResourceLocation location = registry.getKey(item);
			return addUniqueItemEntry(ItemEntry.class, entry -> entry.identifier.equals(location), () -> super.addItemEntry(item));
		}
		
		@Override
		public Builder<T> addTag(INamedTag<T> tag) {
			final ResourceLocation location = tag.getName();
			return addUniqueTagEntry(TagEntry.class, entry -> entry.id.equals(location), () -> super.addTag(tag));
		}
		
		private <C extends ItemEntry> Builder<T> addUniqueItemEntry(Class<C> clazz, Predicate<C> predicate, Supplier<Builder<T>> add) {
			return addUnique(clazz, predicate, add);
		}
		
		private <C extends OptionalItemEntry> Builder<T> addUniqueOptionalItemEntry(Class<C> clazz, Predicate<C> predicate, Supplier<Builder<T>> add) {
			return addUnique(clazz, predicate, add);
		}
		
		private <C extends TagEntry> Builder<T> addUniqueTagEntry(Class<C> clazz, Predicate<C> predicate, Supplier<Builder<T>> add) {
			return addUnique(clazz, predicate, add);
		}
		
		private <C extends OptionalTagEntry> Builder<T> addUniqueOptionalTagEntry(Class<C> clazz, Predicate<C> predicate, Supplier<Builder<T>> add) {
			return addUnique(clazz, predicate, add);
		}
		
		private <C extends ITagEntry> Builder<T> addUnique(Class<C> clazz, Predicate<C> predicate, Supplier<Builder<T>> add) {
			final boolean duplicate = getInternalBuilder().getProxyStream() //
					.map(Proxy::getEntry) //
					.filter(clazz::isInstance) //
					.map(clazz::cast) //
					.anyMatch(predicate);
			if (!duplicate) {
				return add.get();
			}
			return this;
		}
		
	}
}
package info.u_team.u_team_core.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.*;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;

import net.minecraft.data.*;
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
	
	protected BetterBuilder<T> getBuilder(ITag.INamedTag<T> tag) {
		final ITag.Builder tagBuilder = getTagBuilder(tag);
		return new BetterBuilder<>(tagBuilder, registry, modid);
	}
	
	protected ITag.Builder getTagBuilder(ITag.INamedTag<T> tag) {
		return tagToBuilder.computeIfAbsent(tag.getName(), location -> new UniqueBuilder());
	}
	
	protected static class BetterBuilder<T> {
		
		private final Registry<T> registry;
		private final TagsProvider.Builder<T> internalBuilder;
		
		public BetterBuilder(ITag.Builder builder, Registry<T> registry, String id) {
			this.registry = registry;
			internalBuilder = new TagsProvider.Builder<T>(builder, registry, id);
		}
		
		@SafeVarargs
		public final BetterBuilder<T> add(T... values) {
			internalBuilder.add(values);
			return this;
		}
		
		@SafeVarargs
		public final BetterBuilder<T> add(INamedTag<T>... values) {
			internalBuilder.addTags(values);
			return this;
		}
		
		@SafeVarargs
		public final BetterBuilder<T> addOptional(T... values) {
			Stream.of(values).map(registry::getKey).forEach(internalBuilder::addOptional);
			return this;
		}
		
		@SafeVarargs
		public final BetterBuilder<T> addOptional(INamedTag<T>... values) {
			Stream.of(values).map(INamedTag::getName).forEach(internalBuilder::addOptionalTag);
			return this;
		}
		
		public final BetterBuilder<T> addOptional(ResourceLocation location) {
			internalBuilder.addOptional(location);
			return this;
		}
		
		public final BetterBuilder<T> addOptionalTag(ResourceLocation location) {
			internalBuilder.addOptionalTag(location);
			return this;
		}
		
	}
	
	private static class UniqueBuilder extends ITag.Builder {
		
		@Override
		public ITag.Builder addProxyTag(Proxy proxyTag) {
			final ResourceLocation identifier = getIdentifier(proxyTag.getEntry());
			final boolean duplicate = getProxyStream() //
					.map(Proxy::getEntry) //
					.anyMatch(entry -> getIdentifier(entry).equals(identifier));
			
			if (!duplicate) {
				return super.addProxyTag(proxyTag);
			}
			return this;
		}
		
		private ResourceLocation getIdentifier(ITagEntry entry) {
			final ResourceLocation identifier;
			if (entry instanceof ItemEntry) {
				identifier = ((ItemEntry) entry).identifier;
			} else if (entry instanceof OptionalItemEntry) {
				identifier = ((OptionalItemEntry) entry).field_242200_a;
			} else if (entry instanceof TagEntry) {
				identifier = ((TagEntry) entry).id;
			} else if (entry instanceof OptionalTagEntry) {
				identifier = ((OptionalTagEntry) entry).field_242201_a;
			} else {
				throw new IllegalArgumentException("Unknown implementation of ITagEntry");
			}
			return identifier;
		}
	}
}
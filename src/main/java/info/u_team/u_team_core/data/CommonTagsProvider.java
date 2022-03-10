package info.u_team.u_team_core.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Maps;

import net.minecraft.core.Registry;
import net.minecraft.data.HashCache;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.Tag;
import net.minecraft.tags.Tag.BuilderEntry;
import net.minecraft.tags.Tag.ElementEntry;
import net.minecraft.tags.Tag.Entry;
import net.minecraft.tags.Tag.OptionalElementEntry;
import net.minecraft.tags.Tag.OptionalTagEntry;
import net.minecraft.tags.Tag.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.data.ExistingFileHelper.IResourceType;
import net.minecraftforge.common.data.ExistingFileHelper.ResourceType;

public abstract class CommonTagsProvider<T> extends CommonProvider {
	
	protected final Registry<T> registry;
	protected final Map<ResourceLocation, Tag.Builder> tagToBuilder;
	
	protected final IResourceType resourceType;
	
	public CommonTagsProvider(GenerationData data, Registry<T> registry) {
		super(data);
		this.registry = registry;
		this.tagToBuilder = Maps.newLinkedHashMap();
		resourceType = new ResourceType(PackType.SERVER_DATA, ".json", "tags/" + getTagFolder());
	}
	
	protected abstract void registerTags();
	
	@Override
	public void run(HashCache cache) {
		tagToBuilder.clear();
		registerTags();
		
		tagToBuilder.forEach((location, builder) -> {
			final List<Tag.BuilderEntry> list = builder.getEntries() //
					.filter(builderEntry -> !builderEntry.entry().verifyIfPresent(id -> tagToBuilder.containsKey(id), id -> registry.getOptional(id).isPresent())) // TODO verify that this is the right replacement
					.filter(this::missing) //
					.collect(Collectors.toList());
			if (!list.isEmpty()) {
				throw new IllegalArgumentException(String.format("Couldn't define tag %s as it is missing following references: %s", location, list.stream().map(Objects::toString).collect(Collectors.joining(","))));
			}
			final var object = builder.serializeToJson();
			final var path = makePath(location);
			try {
				write(cache, object, path);
			} catch (final IOException ex) {
				LOGGER.error(marker, "Could not write data.", ex);
			}
		});
	}
	
	private boolean missing(BuilderEntry proxy) {
		final var entry = proxy.entry();
		if (entry instanceof TagEntry) {
			return !data.getExistingFileHelper().exists(((TagEntry) entry).id, resourceType);
		}
		return false;
	}
	
	protected abstract String getTagFolder();
	
	protected Path makePath(ResourceLocation location) {
		return resolveData(location).resolve("tags").resolve(getTagFolder()).resolve(location.getPath() + ".json");
	}
	
	protected BetterBuilder<T> getBuilder(TagKey<T> tag) {
		final var tagBuilder = getTagBuilder(tag);
		return new BetterBuilder<>(tagBuilder, registry, modid);
	}
	
	protected Tag.Builder getTagBuilder(TagKey<T> tag) {
		return tagToBuilder.computeIfAbsent(tag.location(), location -> {
			data.getExistingFileHelper().trackGenerated(location, resourceType);
			return new UniqueBuilder();
		});
	}
	
	public static class BetterBuilder<T> {
		
		private final Registry<T> registry;
		private final TagsProvider.TagAppender<T> internalBuilder;
		
		public BetterBuilder(Tag.Builder builder, Registry<T> registry, String id) {
			this.registry = registry;
			internalBuilder = new TagsProvider.TagAppender<>(builder, registry, id);
		}
		
		@SafeVarargs
		public final BetterBuilder<T> add(T... values) {
			internalBuilder.add(values);
			return this;
		}
		
		@SafeVarargs
		public final BetterBuilder<T> add(TagKey<T>... values) {
			internalBuilder.addTags(values);
			return this;
		}
		
		@SafeVarargs
		public final BetterBuilder<T> addOptional(T... values) {
			Stream.of(values).map(registry::getKey).forEach(internalBuilder::addOptional);
			return this;
		}
		
		@SafeVarargs
		public final BetterBuilder<T> addOptional(TagKey<T>... values) {
			Stream.of(values).map(TagKey::location).forEach(internalBuilder::addOptionalTag);
			return this;
		}
		
		public BetterBuilder<T> add(T value) {
			internalBuilder.add(value);
			return this;
		}
		
		public BetterBuilder<T> add(TagKey<T> value) {
			internalBuilder.addTag(value);
			return this;
		}
		
		public BetterBuilder<T> addOptional(ResourceLocation location) {
			internalBuilder.addOptional(location);
			return this;
		}
		
		public BetterBuilder<T> addOptionalTag(ResourceLocation location) {
			internalBuilder.addOptionalTag(location);
			return this;
		}
		
	}
	
	private static class UniqueBuilder extends Tag.Builder {
		
		@Override
		public Tag.Builder add(BuilderEntry proxyTag) {
			final var identifier = getIdentifier(proxyTag.entry());
			final var duplicate = getEntries() //
					.map(BuilderEntry::entry) //
					.anyMatch(entry -> getIdentifier(entry).equals(identifier));
			
			if (!duplicate) {
				return super.add(proxyTag);
			}
			return this;
		}
		
		private ResourceLocation getIdentifier(Entry entry) {
			final ResourceLocation identifier;
			if (entry instanceof ElementEntry elementEntry) {
				identifier = elementEntry.id;
			} else if (entry instanceof OptionalElementEntry optionalEntry) {
				identifier = optionalEntry.id;
			} else if (entry instanceof TagEntry tagEntry) {
				identifier = tagEntry.id;
			} else if (entry instanceof OptionalTagEntry optionalTagEntry) {
				identifier = optionalTagEntry.id;
			} else {
				throw new IllegalArgumentException("Unknown implementation of ITagEntry");
			}
			return identifier;
		}
	}
}

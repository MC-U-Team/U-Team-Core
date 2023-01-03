package info.u_team.u_team_core.data;

import info.u_team.u_team_core.util.ReflectionUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.data.ExistingFileHelper.IResourceType;

public abstract class CommonTagsProvider<T> extends TagsProvider<T> implements CommonDataProvider<HolderLookup.Provider> {
	
	private final GenerationData generationData;
	
	protected final IResourceType resourceType;
	
	public CommonTagsProvider(GenerationData generationData, ResourceKey<? extends Registry<T>> registryKey) {
		super(generationData.output(), registryKey, generationData.lookupProviderFuture(), generationData.modid(), generationData.existingFileHelper());
		
		this.generationData = generationData;
		
		// Forge added field. Therefore use reflection instead of at
		resourceType = ReflectionUtil.getValue(ReflectionUtil.findField(TagsProvider.class, "resourceType"), this);
	}
	
	@Override
	public GenerationData getGenerationData() {
		return generationData;
	}
	
	@Override
	protected void addTags(HolderLookup.Provider provider) {
		register(provider);
	}
	
	@Override
	protected TagBuilder getOrCreateRawBuilder(TagKey<T> tagKey) {
		return builders.computeIfAbsent(tagKey.location(), location -> {
			existingFileHelper.trackGenerated(location, resourceType);
			return new UniqueTagBuilder();
		});
	}
	
	private static class UniqueTagBuilder extends TagBuilder {
		
		@Override
		public TagBuilder add(TagEntry entry) {
			final boolean duplicate = entries.stream().anyMatch(otherEntry -> otherEntry.getId().equals(entry.getId()) && otherEntry.tag == entry.tag);
			if (!duplicate) {
				return super.add(entry);
			}
			return this;
		}
	}
}

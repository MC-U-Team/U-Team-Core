package info.u_team.u_team_core.data;

import info.u_team.u_team_core.util.ReflectionUtil;
import net.minecraft.core.Registry;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.data.ExistingFileHelper.IResourceType;

public abstract class CommonTagsProvider<T> extends TagsProvider<T> implements CommonDataProvider.NoParam {
	
	private final GenerationData generationData;
	
	protected final IResourceType resourceType;
	
	public CommonTagsProvider(GenerationData generationData, Registry<T> registry) {
		super(generationData.generator(), registry, generationData.modid(), generationData.existingFileHelper());
		
		this.generationData = generationData;
		
		// Forge added field. Therefore use reflection instead of at
		resourceType = ReflectionUtil.getValue(ReflectionUtil.findField(TagsProvider.class, "resourceType"), this);
	}
	
	@Override
	public GenerationData getGenerationData() {
		return generationData;
	}
	
	@Override
	protected final void addTags() {
		register();
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

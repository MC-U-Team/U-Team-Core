package info.u_team.u_team_core.data;

import net.minecraft.core.Registry;
import net.minecraft.data.tags.TagsProvider;

public abstract class CommonTagsProvider<T> extends TagsProvider<T> implements CommonDataProvider.NoParam {
	
	private final GenerationData generationData;
	
	public CommonTagsProvider(GenerationData generationData, Registry<T> registry) {
		super(generationData.generator(), registry, generationData.modid(), generationData.existingFileHelper());
		
		this.generationData = generationData;
	}
	
	@Override
	public GenerationData getGenerationData() {
		return generationData;
	}
	
	@Override
	protected final void addTags() {
		register();
	}
}

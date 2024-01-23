package info.u_team.u_team_core.data;

import net.minecraftforge.common.data.SoundDefinitionsProvider;

public abstract class CommonSoundProvider extends SoundDefinitionsProvider implements CommonDataProvider.NoParam {
	
	private final GenerationData generationData;
	
	public CommonSoundProvider(GenerationData generationData) {
		super(generationData.output(), generationData.modid(), generationData.existingFileHelper());
		this.generationData = generationData;
	}
	
	@Override
	public final void registerSounds() {
		register(null);
	}
	
	@Override
	public String getName() {
		return "Sounds: " + nameSuffix();
	}
	
	@Override
	public GenerationData getGenerationData() {
		return generationData;
	}
}

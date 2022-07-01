package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import com.google.gson.JsonElement;

import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.JsonCodecProvider;

public abstract class CommonJsonCodecsProvider implements CommonDataProvider<Consumer<JsonCodecProvider<?>>> {
	
	private final GenerationData generationData;
	
	public CommonJsonCodecsProvider(GenerationData generationData) {
		this.generationData = generationData;
	}
	
	@Override
	public GenerationData getGenerationData() {
		return generationData;
	}
	
	@Override
	public void run(CachedOutput cachedOutput) throws IOException {
		register(provider -> {
			try {
				provider.run(cachedOutput);
			} catch (final IOException ex) {
				throw new RuntimeException(ex);
			}
		});
	}
	
	@Override
	public String getName() {
		return "Json-Codecs-Provider";
	}
	
	protected <T> JsonCodecProvider<T> dataPackRegistry(RegistryOps<JsonElement> registryOps, ResourceKey<Registry<T>> registryKey, Map<ResourceLocation, T> entries) {
		return JsonCodecProvider.forDatapackRegistry(generationData.generator(), generationData.existingFileHelper(), modid(), registryOps, registryKey, entries);
	}
}

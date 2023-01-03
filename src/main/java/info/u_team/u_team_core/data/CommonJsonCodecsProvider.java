package info.u_team.u_team_core.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.minecraft.data.CachedOutput;
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
	public CompletableFuture<?> run(CachedOutput cachedOutput) {
		final List<CompletableFuture<?>> futures = new ArrayList<>();
		register(provider -> {
			futures.add(provider.run(cachedOutput));
		});
		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}
	
	@Override
	public String getName() {
		return "Json-Codecs-Provider";
	}
	
	// TODO use datapack provider??
	// protected <T> JsonCodecProvider<T> dataPackRegistry(RegistryOps<JsonElement> registryOps, ResourceKey<Registry<T>>
	// registryKey, Map<ResourceLocation, T> entries) {
	// return JsonCodecProvider.forDatapackRegistry(generationData.generator(), generationData.existingFileHelper(),
	// modid(), registryOps, registryKey, entries);
	// }
}

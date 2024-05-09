package info.u_team.u_team_core.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput.PathProvider;
import net.minecraft.data.PackOutput.Target;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.WithConditions;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion;

public abstract class CommonGlobalLootModifierProvider implements DataProvider, CommonDataProvider<BiConsumer<String, WithConditions<IGlobalLootModifier>>> {
	
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	private final GenerationData generationData;
	
	private final PathProvider pathProvider;
	
	protected boolean replace;
	
	public CommonGlobalLootModifierProvider(GenerationData generationData) {
		this.generationData = generationData;
		
		pathProvider = generationData.output().createPathProvider(Target.DATA_PACK, "loot_modifiers");
	}
	
	@Override
	public GenerationData getGenerationData() {
		return generationData;
	}
	
	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		final Map<String, WithConditions<IGlobalLootModifier>> serializers = new TreeMap<>();
		
		register((modifier, instance) -> {
			serializers.put(modifier, instance);
		});
		
		final List<CompletableFuture<?>> futures = new ArrayList<>();
		final List<String> entries = serializers.entrySet().stream().map(entry -> {
			final String name = entry.getKey();
			final ResourceLocation location = new ResourceLocation(modid(), name);
			
			futures.add(saveData(cache, IGlobalLootModifier.CONDITIONAL_CODEC, Optional.of(entry.getValue()), pathProvider.json(location)));
			
			return location;
		}).map(ResourceLocation::toString).collect(Collectors.toList());
		
		final JsonObject json = new JsonObject();
		json.addProperty("replace", replace);
		json.add("entries", GSON.toJsonTree(entries));
		
		futures.add(saveData(cache, json, pathProvider.json(new ResourceLocation(NeoForgeVersion.MOD_ID, "global_loot_modifiers"))));
		
		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}
	
	protected void replacing() {
		replace = true;
	}
	
	@Override
	public String getName() {
		return "Global-Loot-Modifier: " + nameSuffix();
	}
	
}

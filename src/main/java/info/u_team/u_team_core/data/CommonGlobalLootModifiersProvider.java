package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.TriConsumer;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator.PathProvider;
import net.minecraft.data.DataGenerator.Target;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CommonGlobalLootModifiersProvider implements DataProvider, CommonDataProvider<TriConsumer<String, Supplier<? extends GlobalLootModifierSerializer<? extends IGlobalLootModifier>>, ? super IGlobalLootModifier>> {
	
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	private final GenerationData generationData;
	
	private final PathProvider pathProvider;
	
	protected boolean replace;
	
	public CommonGlobalLootModifiersProvider(GenerationData generationData) {
		this.generationData = generationData;
		
		pathProvider = generationData.generator().createPathProvider(Target.DATA_PACK, "loot_modifiers");
	}
	
	@Override
	public GenerationData getGenerationData() {
		return generationData;
	}
	
	@Override
	public void run(CachedOutput cache) throws IOException {
		final Map<String, Tuple<GlobalLootModifierSerializer<?>, JsonObject>> serializers = new TreeMap<>();
		
		register((modifier, serializerSupplier, instance) -> {
			final GlobalLootModifierSerializer<IGlobalLootModifier> serializer = CastUtil.uncheckedCast(serializerSupplier.get());
			serializers.put(modifier, new Tuple<>(serializer, serializer.write(instance)));
		});
		
		final List<String> entries = serializers.entrySet().stream().map(entry -> {
			final String name = entry.getKey();
			final var tuple = entry.getValue();
			final JsonObject json = tuple.getB();
			
			final ResourceLocation location = new ResourceLocation(modid(), name);
			
			json.addProperty("type", ForgeRegistries.LOOT_MODIFIER_SERIALIZERS.get().getKey(tuple.getA()).toString());
			
			CommonDataProvider.saveData(cache, json, pathProvider.json(location), "Cannot save global loot modifier");
			
			return location;
		}).map(ResourceLocation::toString).collect(Collectors.toList());
		
		final JsonObject json = new JsonObject();
		json.addProperty("replace", replace);
		json.add("entries", GSON.toJsonTree(entries));
		
		CommonDataProvider.saveData(cache, json, pathProvider.json(new ResourceLocation("forge", "global_loot_modifiers")), "Cannot save global loot modifiers list");
	}
	
	protected void replacing() {
		replace = true;
	}
	
	@Override
	public String getName() {
		return "Global-Loot-Modifiers";
	}
	
}

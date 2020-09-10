package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import info.u_team.u_team_core.util.TriConsumer;
import net.minecraft.data.DirectoryCache;
import net.minecraft.util.*;
import net.minecraftforge.common.loot.*;

public abstract class CommonGlobalLootModifiersProvider extends CommonProvider {
	
	protected boolean replace;
	
	public CommonGlobalLootModifiersProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void act(DirectoryCache cache) throws IOException {
		final Map<String, Tuple<GlobalLootModifierSerializer<?>, JsonObject>> serializers = new TreeMap<>();
		
		registerGlobalLootModifiers((modifier, serializerSupplier, instance) -> {
			final GlobalLootModifierSerializer<IGlobalLootModifier> serializer = serializerSupplier.get();
			serializers.put(modifier, new Tuple<>(serializer, serializer.write(instance)));
		});
		
		final List<String> entries = serializers.entrySet().stream().map(entry -> {
			final String name = entry.getKey();
			final Tuple<GlobalLootModifierSerializer<?>, JsonObject> tuple = entry.getValue();
			final JsonObject json = tuple.getB();
			
			json.addProperty("type", tuple.getA().getRegistryName().toString());
			
			try {
				write(cache, json, resolveModData().resolve("loot_modifiers").resolve(name + ".json"));
			} catch (final IOException ex) {
				LOGGER.error(marker, "Could not write data.", ex);
			}
			return new ResourceLocation(modid, name);
		}).map(ResourceLocation::toString).collect(Collectors.toList());
		
		final JsonObject json = new JsonObject();
		json.addProperty("replace", replace);
		json.add("entries", GSON.toJsonTree(entries));
		
		try {
			write(cache, json, path.resolve("data").resolve("forge").resolve("loot_modifiers").resolve("global_loot_modifiers.json"));
		} catch (final IOException ex) {
			LOGGER.error(marker, "Could not write data.", ex);
		}
	}
	
	protected void replacing() {
		replace = true;
	}
	
	protected abstract <T extends IGlobalLootModifier> void registerGlobalLootModifiers(TriConsumer<String, Supplier<GlobalLootModifierSerializer<T>>, T> consumer);
	
	@Override
	public String getName() {
		return "Global-Loot-Modifiers";
	}
	
}

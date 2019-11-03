package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.data.DirectoryCache;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.world.biome.Biome;

public abstract class CommonLanguagesProvider extends CommonProvider {
	
	protected final Map<String, Map<String, String>> data;
	
	public CommonLanguagesProvider(GenerationData data) {
		super(data);
		this.data = new HashMap<>();
	}
	
	@Override
	public void act(DirectoryCache cache) throws IOException {
		addTranslations();
		data.forEach((locale, map) -> {
			if (!map.isEmpty()) {
				try {
					write(cache, GSON.toJsonTree(map), resolveModAssets().resolve("lang").resolve(locale + ".json"));
				} catch (IOException ex) {
					LOGGER.error(marker, "Could not write data.", ex);
				}
			}
		});
	}
	
	public abstract void addTranslations();
	
	@Override
	public String getName() {
		return "Languages";
	}
	
	protected void add(ItemGroup key, String name) {
		add(key.getTranslationKey(), name);
	}
	
	protected void addBlock(Supplier<Block> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(Block key, String name) {
		add(key.getTranslationKey(), name);
	}
	
	protected void addItem(Supplier<Item> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(Item key, String name) {
		add(key.getTranslationKey(), name);
	}
	
	protected void addItemStack(Supplier<ItemStack> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(ItemStack key, String name) {
		add(key.getTranslationKey(), name);
	}
	
	protected void addEnchantment(Supplier<Enchantment> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(Enchantment key, String name) {
		add(key.getName(), name);
	}
	
	protected void addBiome(Supplier<Biome> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(Biome key, String name) {
		add(key.getTranslationKey(), name);
	}
	
	protected void addEffect(Supplier<Effect> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(Effect key, String name) {
		add(key.getName(), name);
	}
	
	protected void addEntityType(Supplier<EntityType<?>> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(EntityType<?> key, String name) {
		add(key.getTranslationKey(), name);
	}
	
	protected void add(String key, String value) {
		add("en_us", key, value);
	}
	
	protected void add(String locale, ItemGroup key, String name) {
		add(locale, key.getTranslationKey(), name);
	}
	
	protected void addBlock(String locale, Supplier<Block> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, Block key, String name) {
		add(locale, key.getTranslationKey(), name);
	}
	
	protected void addItem(String locale, Supplier<Item> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, Item key, String name) {
		add(locale, key.getTranslationKey(), name);
	}
	
	protected void addItemStack(String locale, Supplier<ItemStack> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, ItemStack key, String name) {
		add(locale, key.getTranslationKey(), name);
	}
	
	protected void addEnchantment(String locale, Supplier<Enchantment> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, Enchantment key, String name) {
		add(locale, key.getName(), name);
	}
	
	protected void addBiome(String locale, Supplier<Biome> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, Biome key, String name) {
		add(locale, key.getTranslationKey(), name);
	}
	
	protected void addEffect(String locale, Supplier<Effect> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, Effect key, String name) {
		add(locale, key.getName(), name);
	}
	
	protected void addEntityType(String locale, Supplier<EntityType<?>> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, EntityType<?> key, String name) {
		add(locale, key.getTranslationKey(), name);
	}
	
	protected void add(String locale, String key, String value) {
		final Map<String, String> map = data.computeIfAbsent(locale, unused -> new TreeMap<>());
		if (map.put(key, value) != null) {
			throw new IllegalStateException("Duplicate translation key " + key);
		}
	}
	
}

package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator.PathProvider;
import net.minecraft.data.DataGenerator.Target;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public abstract class CommonLanguageProvider implements DataProvider, CommonDataProvider.NoParam {
	
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private static final String DEFAULT_LANG = "en_us";
	
	private final GenerationData generationData;
	
	private final Map<String, Map<String, String>> data;
	
	private final PathProvider pathProvider;
	
	public CommonLanguageProvider(GenerationData generationData) {
		this.generationData = generationData;
		data = new HashMap<>();
		
		pathProvider = generationData.generator().createPathProvider(Target.RESOURCE_PACK, "lang");
	}
	
	@Override
	public GenerationData getGenerationData() {
		return generationData;
	}
	
	@Override
	public void run(CachedOutput cache) throws IOException {
		register(null);
		
		data.forEach((locale, map) -> {
			if (!map.isEmpty()) {
				CommonDataProvider.saveData(cache, GSON.toJsonTree(map), pathProvider.json(new ResourceLocation(modid(), locale)), "Cannot write language file");
			}
		});
	}
	
	@Override
	public String getName() {
		return "Languages";
	}
	
	protected void add(CreativeModeTab key, String name) {
		final Component component = key.getDisplayName();
		if (component.getContents() instanceof final TranslatableContents translatableContents) {
			add(translatableContents.getKey(), name);
		}
	}
	
	protected void addBlock(Supplier<? extends Block> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(Block key, String name) {
		add(key.getDescriptionId(), name);
	}
	
	protected void addItem(Supplier<? extends Item> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(Item key, String name) {
		add(key.getDescriptionId(), name);
	}
	
	protected void addItemStack(Supplier<? extends ItemStack> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(ItemStack key, String name) {
		add(key.getDescriptionId(), name);
	}
	
	protected void addEnchantment(Supplier<? extends Enchantment> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(Enchantment key, String name) {
		add(key.getDescriptionId(), name);
	}
	
	protected void addEffect(Supplier<? extends MobEffect> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(MobEffect key, String name) {
		add(key.getDescriptionId(), name);
	}
	
	protected void addEntityType(Supplier<? extends EntityType<?>> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(EntityType<?> key, String name) {
		add(key.getDescriptionId(), name);
	}
	
	protected void add(Supplier<? extends Fluid> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(Fluid key, String name) {
		add(key.getFluidType().getDescriptionId(), name);
	}
	
	protected void add(String key, String value) {
		add(DEFAULT_LANG, key, value);
	}
	
	protected void add(String locale, CreativeModeTab key, String name) {
		final Component component = key.getDisplayName();
		if (component.getContents() instanceof final TranslatableContents translatableContents) {
			add(locale, translatableContents.getKey(), name);
		}
	}
	
	protected void addBlock(String locale, Supplier<? extends Block> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, Block key, String name) {
		add(locale, key.getDescriptionId(), name);
	}
	
	protected void addItem(String locale, Supplier<? extends Item> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, Item key, String name) {
		add(locale, key.getDescriptionId(), name);
	}
	
	protected void addItemStack(String locale, Supplier<? extends ItemStack> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, ItemStack key, String name) {
		add(locale, key.getDescriptionId(), name);
	}
	
	protected void addEnchantment(String locale, Supplier<? extends Enchantment> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, Enchantment key, String name) {
		add(locale, key.getDescriptionId(), name);
	}
	
	protected void addEffect(String locale, Supplier<? extends MobEffect> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, MobEffect key, String name) {
		add(locale, key.getDescriptionId(), name);
	}
	
	protected void addEntityType(String locale, Supplier<? extends EntityType<?>> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, EntityType<?> key, String name) {
		add(locale, key.getDescriptionId(), name);
	}
	
	protected void addFluid(String locale, Supplier<? extends Fluid> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, Fluid key, String name) {
		add(locale, key.getFluidType().getDescriptionId(), name);
	}
	
	protected void add(String locale, String key, String value) {
		final Map<String, String> map = data.computeIfAbsent(locale, unused -> new TreeMap<>());
		if (map.put(key, value) != null) {
			throw new IllegalStateException("Duplicate translation key " + key);
		}
	}
	
	// Special item tooltips
	
	protected void addBlockTooltip(Supplier<? extends Block> key, String category, int line, String value) {
		addItemTooltip(() -> key.get().asItem(), category, line, value);
	}
	
	protected void addBlockTooltip(String locale, Supplier<? extends Block> key, String category, int line, String value) {
		addItemTooltip(locale, () -> key.get().asItem(), category, line, value);
	}
	
	protected void addItemTooltip(Supplier<? extends Item> key, String category, int line, String value) {
		if (!category.isEmpty()) {
			category += ".";
		}
		add(key.get().getDescriptionId() + ".tooltip." + category + line, value);
	}
	
	protected void addItemTooltip(String locale, Supplier<? extends Item> key, String category, int line, String value) {
		if (!category.isEmpty()) {
			category += ".";
		}
		add(locale, key.get().getDescriptionId() + ".tooltip." + category + line, value);
	}
	
	protected void addTooltip(String key, String category, int line, String value) {
		if (!category.isEmpty()) {
			category += ".";
		}
		add("general." + modid() + "." + key + ".tooltip." + category + line, value);
	}
	
	protected void addTooltip(String locale, String key, String category, int line, String value) {
		if (!category.isEmpty()) {
			category += ".";
		}
		add(locale, "general." + modid() + "." + key + ".tooltip." + category + line, value);
	}
	
}

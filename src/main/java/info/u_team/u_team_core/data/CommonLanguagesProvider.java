package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.data.DirectoryCache;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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
				} catch (final IOException ex) {
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
		final ITextComponent component = key.getGroupName();
		if (component instanceof TranslationTextComponent) {
			add(((TranslationTextComponent) component).getKey(), name);
		}
	}
	
	protected void addBlock(Supplier<? extends Block> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(Block key, String name) {
		add(key.getTranslationKey(), name);
	}
	
	protected void addItem(Supplier<? extends Item> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(Item key, String name) {
		add(key.getTranslationKey(), name);
	}
	
	protected void addItemStack(Supplier<? extends ItemStack> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(ItemStack key, String name) {
		add(key.getTranslationKey(), name);
	}
	
	protected void addEnchantment(Supplier<? extends Enchantment> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(Enchantment key, String name) {
		add(key.getName(), name);
	}
	
	protected void addEffect(Supplier<? extends Effect> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(Effect key, String name) {
		add(key.getName(), name);
	}
	
	protected void addEntityType(Supplier<? extends EntityType<?>> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(EntityType<?> key, String name) {
		add(key.getTranslationKey(), name);
	}
	
	protected void add(Supplier<? extends Fluid> key, String name) {
		add(key.get(), name);
	}
	
	protected void add(Fluid key, String name) {
		add(key.getAttributes().getTranslationKey(), name);
	}
	
	protected void add(String key, String value) {
		add("en_us", key, value);
	}
	
	protected void add(String locale, ItemGroup key, String name) {
		final ITextComponent component = key.getGroupName();
		if (component instanceof TranslationTextComponent) {
			add(locale, ((TranslationTextComponent) component).getKey(), name);
		}
	}
	
	protected void addBlock(String locale, Supplier<? extends Block> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, Block key, String name) {
		add(locale, key.getTranslationKey(), name);
	}
	
	protected void addItem(String locale, Supplier<? extends Item> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, Item key, String name) {
		add(locale, key.getTranslationKey(), name);
	}
	
	protected void addItemStack(String locale, Supplier<? extends ItemStack> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, ItemStack key, String name) {
		add(locale, key.getTranslationKey(), name);
	}
	
	protected void addEnchantment(String locale, Supplier<? extends Enchantment> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, Enchantment key, String name) {
		add(locale, key.getName(), name);
	}
	
	protected void addEffect(String locale, Supplier<? extends Effect> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, Effect key, String name) {
		add(locale, key.getName(), name);
	}
	
	protected void addEntityType(String locale, Supplier<? extends EntityType<?>> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, EntityType<?> key, String name) {
		add(locale, key.getTranslationKey(), name);
	}
	
	protected void addFluid(String locale, Supplier<? extends Fluid> key, String name) {
		add(locale, key.get(), name);
	}
	
	protected void add(String locale, Fluid key, String name) {
		add(locale, key.getAttributes().getTranslationKey(), name);
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
		add(key.get().getTranslationKey() + ".tooltip." + category + line, value);
	}
	
	protected void addItemTooltip(String locale, Supplier<? extends Item> key, String category, int line, String value) {
		if (!category.isEmpty()) {
			category += ".";
		}
		add(locale, key.get().getTranslationKey() + ".tooltip." + category + line, value);
	}
	
	protected void addTooltip(String key, String category, int line, String value) {
		if (!category.isEmpty()) {
			category += ".";
		}
		add("general." + modid + "." + key + ".tooltip." + category + line, value);
	}
	
	protected void addTooltip(String locale, String key, String category, int line, String value) {
		if (!category.isEmpty()) {
			category += ".";
		}
		add(locale, "general." + modid + "." + key + ".tooltip." + category + line, value);
	}
	
}

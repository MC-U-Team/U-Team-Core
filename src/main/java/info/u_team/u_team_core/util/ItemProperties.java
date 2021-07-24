package info.u_team.u_team_core.util;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class ItemProperties extends Properties {
	
	public ItemProperties() {
	}
	
	public ItemProperties(Properties properties) {
		maxStackSize = properties.maxStackSize;
		maxDamage = properties.maxDamage;
		craftingRemainingItem = properties.craftingRemainingItem;
		category = properties.category;
		rarity = properties.rarity;
		foodProperties = properties.foodProperties;
		isFireResistant = properties.isFireResistant;
		setValueCanRepair(getValueCanRepair(properties));
		setValueToolClasses(Maps.newHashMap(getValueToolClasses(properties)));
		setValueIster(getValueIster(properties));
	}
	
	private boolean getValueCanRepair(Properties properties) {
		return ObfuscationReflectionHelper.getPrivateValue(Properties.class, properties, "canRepair");
	}
	
	private void setValueCanRepair(boolean value) {
		ObfuscationReflectionHelper.setPrivateValue(Properties.class, this, value, "canRepair");
	}
	
	private Map<ToolType, Integer> getValueToolClasses(Properties properties) {
		return ObfuscationReflectionHelper.getPrivateValue(Properties.class, properties, "toolClasses");
	}
	
	private void setValueToolClasses(Map<ToolType, Integer> value) {
		ObfuscationReflectionHelper.setPrivateValue(Properties.class, this, value, "toolClasses");
	}
	
	private Supplier<Callable<BlockEntityWithoutLevelRenderer>> getValueIster(Properties properties) {
		return ObfuscationReflectionHelper.getPrivateValue(Properties.class, properties, "ister");
	}
	
	private void setValueIster(Supplier<Callable<BlockEntityWithoutLevelRenderer>> value) {
		ObfuscationReflectionHelper.setPrivateValue(Properties.class, this, value, "ister");
	}
}

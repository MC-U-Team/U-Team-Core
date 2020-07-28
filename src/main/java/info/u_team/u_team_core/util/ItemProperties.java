package info.u_team.u_team_core.util;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ItemProperties extends Properties {
	
	public ItemProperties() {
	}
	
	public ItemProperties(Properties properties) {
		maxStackSize = properties.maxStackSize;
		maxDamage = properties.maxDamage;
		containerItem = properties.containerItem;
		group = properties.group;
		rarity = properties.rarity;
		food = properties.food;
		burnable = properties.burnable;
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
	
	private Supplier<Callable<ItemStackTileEntityRenderer>> getValueIster(Properties properties) {
		return ObfuscationReflectionHelper.getPrivateValue(Properties.class, properties, "ister");
	}
	
	private void setValueIster(Supplier<Callable<ItemStackTileEntityRenderer>> value) {
		ObfuscationReflectionHelper.setPrivateValue(Properties.class, this, value, "ister");
	}
}

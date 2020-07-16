package info.u_team.u_team_core.util;

import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class BlockProperties extends Properties {
	
	public BlockProperties(Material material, MaterialColor color) {
		super(material, color);
	}
	
	public BlockProperties(Properties properties) {
		super(properties.material, properties.field_235800_b_);
		blocksMovement = properties.blocksMovement;
		soundType = properties.soundType;
		lightLevel = properties.lightLevel;
		resistance = properties.resistance;
		hardness = properties.hardness;
		requiresTool = properties.requiresTool;
		ticksRandomly = properties.ticksRandomly;
		slipperiness = properties.slipperiness;
		speedFactor = properties.speedFactor;
		jumpFactor = properties.jumpFactor;
		lootTable = properties.lootTable;
		isSolid = properties.isSolid;
		isAir = properties.isAir;
		propagatesLightDownwards = properties.propagatesLightDownwards;
		isOpaque = properties.isOpaque;
		suffocates = properties.suffocates;
		blocksVision = properties.blocksVision;
		needsPostProcessing = properties.needsPostProcessing;
		emmissiveRendering = properties.emmissiveRendering;
		variableOpacity = properties.variableOpacity;
		
		setValueHarvestLevel(getValueHarvestLevel(properties));
		setValueHarvestTool(getValueHarvestTool(properties));
		setLootTableSupplier(getLootTableSupplier(properties));
	}
	
	private int getValueHarvestLevel(Properties properties) {
		return ObfuscationReflectionHelper.getPrivateValue(Properties.class, properties, "harvestLevel");
	}
	
	private void setValueHarvestLevel(int value) {
		ObfuscationReflectionHelper.setPrivateValue(Properties.class, this, value, "harvestLevel");
	}
	
	private ToolType getValueHarvestTool(Properties properties) {
		return ObfuscationReflectionHelper.getPrivateValue(Properties.class, properties, "harvestTool");
	}
	
	private void setValueHarvestTool(ToolType value) {
		ObfuscationReflectionHelper.setPrivateValue(Properties.class, this, value, "harvestTool");
	}
	
	private Supplier<ResourceLocation> getLootTableSupplier(Properties properties) {
		return ObfuscationReflectionHelper.getPrivateValue(Properties.class, properties, "lootTableSupplier");
	}
	
	private void setLootTableSupplier(Supplier<ResourceLocation> value) {
		ObfuscationReflectionHelper.setPrivateValue(Properties.class, this, value, "lootTableSupplier");
	}
}

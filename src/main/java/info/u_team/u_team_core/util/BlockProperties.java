package info.u_team.u_team_core.util;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class BlockProperties extends Properties {
	
	public BlockProperties(Material material, MaterialColor color) {
		super(material, color);
	}
	
	public BlockProperties(Properties properties) {
		super(properties.material, properties.materialColor);
		hasCollision = properties.hasCollision;
		soundType = properties.soundType;
		lightEmission = properties.lightEmission;
		explosionResistance = properties.explosionResistance;
		destroyTime = properties.destroyTime;
		requiresCorrectToolForDrops = properties.requiresCorrectToolForDrops;
		isRandomlyTicking = properties.isRandomlyTicking;
		friction = properties.friction;
		speedFactor = properties.speedFactor;
		jumpFactor = properties.jumpFactor;
		drops = properties.drops;
		canOcclude = properties.canOcclude;
		isAir = properties.isAir;
		isValidSpawn = properties.isValidSpawn;
		isRedstoneConductor = properties.isRedstoneConductor;
		isSuffocating = properties.isSuffocating;
		isViewBlocking = properties.isViewBlocking;
		hasPostProcess = properties.hasPostProcess;
		emissiveRendering = properties.emissiveRendering;
		dynamicShape = properties.dynamicShape;
		
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

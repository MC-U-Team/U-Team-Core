package info.u_team.u_team_core.util;

import java.util.function.Supplier;

import net.minecraft.block.Block.Properties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class BlockProperties extends Properties {
	
	public BlockProperties(Material material, MaterialColor color) {
		super(material, color);
	}
	
	public BlockProperties(Properties properties) {
		super(properties.material, properties.mapColor);
		blocksMovement = properties.blocksMovement;
		soundType = properties.soundType;
		lightValue = properties.lightValue;
		resistance = properties.resistance;
		hardness = properties.hardness;
		ticksRandomly = properties.ticksRandomly;
		slipperiness = properties.slipperiness;
		field_226893_j_ = properties.field_226893_j_;
		field_226894_k_ = properties.field_226894_k_;
		lootTable = properties.lootTable;
		field_226895_m_ = properties.field_226895_m_;
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
	
	public Material getMaterial() {
		return material;
	}
	
	public MaterialColor getMapColor() {
		return mapColor;
	}
	
	public boolean isBlocksMovement() {
		return blocksMovement;
	}
	
	public SoundType getSoundType() {
		return soundType;
	}
	
	public int getLightValue() {
		return lightValue;
	}
	
	public float getResistance() {
		return resistance;
	}
	
	public float getHardness() {
		return hardness;
	}
	
	public boolean isTickRandomly() {
		return ticksRandomly;
	}
	
	public float getSlipperiness() {
		return slipperiness;
	}
	
	public ResourceLocation getLootTable() {
		return lootTable;
	}
	
	public boolean isVariableOpacity() {
		return variableOpacity;
	}
	
	public int getHarvestLevel() {
		return getValueHarvestLevel(this);
	}
	
	public ToolType getHarvestTool() {
		return getValueHarvestTool(this);
	}
}

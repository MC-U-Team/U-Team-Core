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
		field_235803_e_ = properties.field_235803_e_;
		resistance = properties.resistance;
		field_235806_h_ = properties.field_235806_h_;
		ticksRandomly = properties.ticksRandomly;
		slipperiness = properties.slipperiness;
		speedFactor = properties.speedFactor;
		jumpFactor = properties.jumpFactor;
		lootTable = properties.lootTable;
		isSolid = properties.isSolid;
		field_235813_o_ = properties.field_235813_o_;
		field_235814_p_ = properties.field_235814_p_;
		field_235814_p_ = properties.field_235814_p_;
		field_235815_q_ = properties.field_235815_q_;
		field_235816_r_ = properties.field_235816_r_;
		field_235817_s_ = properties.field_235817_s_;
		field_235818_t_ = properties.field_235818_t_;
		field_235819_u_ = properties.field_235819_u_;
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

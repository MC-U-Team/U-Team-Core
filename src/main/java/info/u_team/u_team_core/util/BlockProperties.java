package info.u_team.u_team_core.util;

import net.minecraft.block.Block.Properties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.*;
import net.minecraft.util.ResourceLocation;

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
		field_222381_j = properties.field_222381_j;
		variableOpacity = properties.variableOpacity;
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
		return field_222381_j;
	}
	
	public boolean isVariableOpacity() {
		return variableOpacity;
	}
	
}

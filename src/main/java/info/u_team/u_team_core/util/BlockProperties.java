package info.u_team.u_team_core.util;

import net.minecraft.block.Block.Properties;
import net.minecraft.block.material.*;

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
		needsRandomTick = properties.needsRandomTick;
		slipperiness = properties.slipperiness;
		variableOpacity = properties.variableOpacity;
	}
}

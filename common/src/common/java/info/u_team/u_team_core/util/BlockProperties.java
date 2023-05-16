package info.u_team.u_team_core.util;

import java.util.List;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

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
		spawnParticlesOnBreak = properties.spawnParticlesOnBreak;
		isValidSpawn = properties.isValidSpawn;
		isRedstoneConductor = properties.isRedstoneConductor;
		isSuffocating = properties.isSuffocating;
		isViewBlocking = properties.isViewBlocking;
		hasPostProcess = properties.hasPostProcess;
		emissiveRendering = properties.emissiveRendering;
		dynamicShape = properties.dynamicShape;
		requiredFeatures = properties.requiredFeatures;
		offsetFunction = properties.offsetFunction;
		
		Extension.INSTANCES.forEach(extension -> extension.copy(this, properties));
	}
	
	public interface Extension {
		
		List<Extension> INSTANCES = ServiceUtil.loadAll(Extension.class);
		
		void copy(BlockProperties ourProperties, Properties properties);
	}
}

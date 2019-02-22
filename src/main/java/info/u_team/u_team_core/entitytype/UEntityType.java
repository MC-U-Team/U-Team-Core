package info.u_team.u_team_core.entitytype;

import java.util.function.Function;

import info.u_team.u_team_core.api.registry.IUEntityType;
import net.minecraft.entity.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages.SpawnEntity;

public class UEntityType<T extends Entity> extends EntityType<T> implements IUEntityType {
	
	protected final String name;
	
	public UEntityType(String name, Class<? extends T> clazz, Function<? super World, ? extends T> factory, boolean serializable, boolean summonable, boolean useVanillaSpawning, Function<SpawnEntity, Entity> customSpawnCallback, boolean hasCustomTracking, int range, int updateFreq, boolean sendVelocityUpdates) {
		super(clazz, factory, serializable, summonable, null, useVanillaSpawning, customSpawnCallback, hasCustomTracking, range, updateFreq, sendVelocityUpdates);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

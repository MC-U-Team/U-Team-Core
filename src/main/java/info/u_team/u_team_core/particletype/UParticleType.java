package info.u_team.u_team_core.particletype;

import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.particles.*;
import net.minecraft.particles.IParticleData.IDeserializer;

public class UParticleType<T extends IParticleData> extends ParticleType<T> implements IURegistryType {
	
	protected final String name;
	
	public UParticleType(String name, boolean alwaysShow, IDeserializer<T> deserializer) {
		super(alwaysShow, deserializer);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

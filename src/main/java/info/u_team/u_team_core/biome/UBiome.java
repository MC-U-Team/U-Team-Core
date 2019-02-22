package info.u_team.u_team_core.biome;

import info.u_team.u_team_core.api.registry.IUBiome;
import net.minecraft.world.biome.Biome;

public class UBiome extends Biome implements IUBiome {
	
	protected final String name;
	
	public UBiome(String name, BiomeBuilder biomeBuilder) {
		super(biomeBuilder);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

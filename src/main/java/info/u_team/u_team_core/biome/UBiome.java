package info.u_team.u_team_core.biome;

import info.u_team.u_team_core.api.registry.IUBiome;
import net.minecraft.world.biome.Biome;

/**
 * Biome API<br>
 * -> Basic Biome
 * 
 * @date 05.07.2018
 * @author HyCraftHD
 */
public class UBiome extends Biome implements IUBiome {
	
	protected String name;
	
	public UBiome(String name) {
		super(new BiomeProperties(name));
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}

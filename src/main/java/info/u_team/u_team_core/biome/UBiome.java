package info.u_team.u_team_core.biome;

import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.world.biome.Biome;

public class UBiome extends Biome implements IURegistryType {
	
	protected final String name;
	
	public UBiome(String name, Builder builder) {
		super(builder);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

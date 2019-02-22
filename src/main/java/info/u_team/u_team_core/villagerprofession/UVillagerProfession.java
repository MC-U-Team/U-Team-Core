package info.u_team.u_team_core.villagerprofession;

import info.u_team.u_team_core.api.registry.IUVillagerProfession;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public class UVillagerProfession extends VillagerProfession implements IUVillagerProfession {
	
	protected final String name;
	
	public UVillagerProfession(String name, String texture, String zombie) {
		super(name, texture, zombie);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

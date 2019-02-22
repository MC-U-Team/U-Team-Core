package info.u_team.u_team_core.dimension;

import info.u_team.u_team_core.api.registry.IUModDimension;
import net.minecraftforge.common.ModDimension;

public abstract class UModDimension extends ModDimension implements IUModDimension {
	
	protected final String name;
	
	public UModDimension(String name) {
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

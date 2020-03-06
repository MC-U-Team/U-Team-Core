package info.u_team.u_team_core.fluid;

import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class USourceFluid extends ForgeFlowingFluid.Source implements IURegistryType {
	
	protected final String name;
	
	public USourceFluid(String name, Properties properties) {
		super(properties);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
}
package info.u_team.u_team_core.fluid;

import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class UFlowingFluid extends ForgeFlowingFluid.Flowing implements IURegistryType {
	
	protected final String name;
	
	public UFlowingFluid(String name, Properties properties) {
		super(properties);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
}
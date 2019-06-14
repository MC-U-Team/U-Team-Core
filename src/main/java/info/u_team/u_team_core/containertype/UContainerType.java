package info.u_team.u_team_core.containertype;

import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.inventory.container.*;

public class UContainerType<T extends Container> extends ContainerType<T> implements IURegistryType {
	
	protected final String name;
	
	public UContainerType(String name, IFactory<T> factory) {
		super(factory);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

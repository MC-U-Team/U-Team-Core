package info.u_team.u_team_core.containertype;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.network.IContainerFactory;

public class UContainerType<T extends Container> extends ContainerType<T> {
	
	public UContainerType(IContainerFactory<T> forgeFactory) {
		this((IFactory<T>) forgeFactory);
	}
	
	public UContainerType(IFactory<T> factory) {
		super(factory);
	}
}

package info.u_team.u_team_core.tileentity;

import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntityType;

public abstract class UTileEntityContainer extends UTileEntity implements INamedContainerProvider {
	
	public UTileEntityContainer(TileEntityType<?> type) {
		super(type);
	}
}

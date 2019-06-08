package info.u_team.u_team_core.tileentity;

import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.*;

public abstract class UTileEntityContainer extends UTileEntity implements INamedContainerProvider {
	
	public UTileEntityContainer(TileEntityType<?> type) {
		super(type);
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(getType().getRegistryName().toString());
	}
}

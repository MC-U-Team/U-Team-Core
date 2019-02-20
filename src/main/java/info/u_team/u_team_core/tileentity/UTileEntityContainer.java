package info.u_team.u_team_core.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraft.world.IInteractionObject;

public abstract class UTileEntityContainer extends UTileEntity implements IInteractionObject {
	
	public UTileEntityContainer(TileEntityType<?> type) {
		super(type);
	}
	
	@Override
	public boolean hasCustomName() {
		return false;
	}
	
	@Override
	public ITextComponent getCustomName() {
		return null;
	}
	
	@Override
	public ITextComponent getName() {
		return new TextComponentString(getType().getRegistryName().toString());
	}
	
	@Override
	public String getGuiID() {
		return getGui().toString();
	}
	
	public abstract ResourceLocation getGui();
	
}

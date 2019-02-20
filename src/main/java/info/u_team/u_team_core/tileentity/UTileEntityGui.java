package info.u_team.u_team_core.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;

public abstract class UTileEntityGui extends UTileEntity implements IInteractionObject {
	
	public UTileEntityGui(TileEntityType<?> type) {
		super(type);
	}
	
	@Override
	public ITextComponent getCustomName() {
		return null;
	}
	
	@Override
	public ITextComponent getName() {
		return null;
	}
	
	@Override
	public boolean hasCustomName() {
		return false;
	}
	
	@Override
	public String getGuiID() {
		return getGui().toString();
	}
	
	public abstract ResourceLocation getGui();
	
}

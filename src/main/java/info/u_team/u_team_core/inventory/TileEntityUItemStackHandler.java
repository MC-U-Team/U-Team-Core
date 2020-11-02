package info.u_team.u_team_core.inventory;

import net.minecraft.tileentity.TileEntity;

public class TileEntityUItemStackHandler extends UItemStackHandler {
	
	protected final TileEntity tileEntity;
	
	public TileEntityUItemStackHandler(int size, TileEntity tileEntity) {
		super(size);
		this.tileEntity = tileEntity;
	}
	
	@Override
	public void onContentsChanged(int slot) {
		tileEntity.markDirty();
	}
	
}

package info.u_team.u_team_core.inventory;

import net.minecraft.tileentity.TileEntity;

public class TileEntityUFluidStackHandler extends UFluidStackHandler {
	
	protected final TileEntity tileEntity;
	
	public TileEntityUFluidStackHandler(int size, int capacity, TileEntity tileEntity) {
		super(size, capacity);
		this.tileEntity = tileEntity;
	}
	
	public TileEntityUFluidStackHandler(int size, TileEntity tileEntity) {
		super(size);
		this.tileEntity = tileEntity;
	}
	
	@Override
	public void onContentsChanged(int tank) {
		tileEntity.markDirty();
	}
	
}
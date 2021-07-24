package info.u_team.u_team_core.inventory;

import net.minecraft.world.level.block.entity.BlockEntity;

public class TileEntityUFluidStackHandler extends UFluidStackHandler {
	
	protected final BlockEntity tileEntity;
	
	public TileEntityUFluidStackHandler(int size, int capacity, BlockEntity tileEntity) {
		super(size, capacity);
		this.tileEntity = tileEntity;
	}
	
	public TileEntityUFluidStackHandler(int size, BlockEntity tileEntity) {
		super(size);
		this.tileEntity = tileEntity;
	}
	
	@Override
	public void onContentsChanged(int tank) {
		tileEntity.setChanged();
	}
	
}

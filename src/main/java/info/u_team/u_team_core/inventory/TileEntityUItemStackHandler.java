package info.u_team.u_team_core.inventory;

import net.minecraft.world.level.block.entity.BlockEntity;

public class TileEntityUItemStackHandler extends UItemStackHandler {
	
	protected final BlockEntity tileEntity;
	
	public TileEntityUItemStackHandler(int size, BlockEntity tileEntity) {
		super(size);
		this.tileEntity = tileEntity;
	}
	
	@Override
	public void onContentsChanged(int slot) {
		tileEntity.setChanged();
	}
	
}

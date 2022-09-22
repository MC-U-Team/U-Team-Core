package info.u_team.u_team_core.inventory;

import net.minecraft.world.level.block.entity.BlockEntity;

public class BlockEntityUItemStackHandler extends UItemStackHandler {
	
	protected final BlockEntity blockEntity;
	
	public BlockEntityUItemStackHandler(int size, BlockEntity blockEntity) {
		super(size);
		this.blockEntity = blockEntity;
	}
	
	@Override
	public void onContentsChanged(int slot) {
		blockEntity.setChanged();
	}
	
}

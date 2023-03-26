package info.u_team.u_team_core.inventory;

import net.minecraft.world.level.block.entity.BlockEntity;

public class BlockEntityUFluidStackHandler extends UFluidStackHandler {
	
	protected final BlockEntity blockEntity;
	
	public BlockEntityUFluidStackHandler(int size, int capacity, BlockEntity blockEntity) {
		super(size, capacity);
		this.blockEntity = blockEntity;
	}
	
	public BlockEntityUFluidStackHandler(int size, BlockEntity blockEntity) {
		super(size);
		this.blockEntity = blockEntity;
	}
	
	@Override
	public void onContentsChanged(int tank) {
		blockEntity.setChanged();
	}
	
}

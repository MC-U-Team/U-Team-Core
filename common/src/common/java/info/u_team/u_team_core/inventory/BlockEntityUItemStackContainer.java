package info.u_team.u_team_core.inventory;

import net.minecraft.world.level.block.entity.BlockEntity;

public class BlockEntityUItemStackContainer extends UItemStackContainer {
	
	protected final BlockEntity blockEntity;
	
	public BlockEntityUItemStackContainer(int size, BlockEntity blockEntity) {
		super(size);
		this.blockEntity = blockEntity;
	}
	
	@Override
	public void setChanged() {
		super.setChanged();
		blockEntity.setChanged();
	}
	
}

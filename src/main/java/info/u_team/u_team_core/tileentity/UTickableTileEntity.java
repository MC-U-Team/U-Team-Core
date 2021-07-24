package info.u_team.u_team_core.tileentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickableBlockEntity;

public abstract class UTickableTileEntity extends UTileEntity implements TickableBlockEntity {
	
	private boolean first;
	
	public UTickableTileEntity(BlockEntityType<?> type) {
		super(type);
	}
	
	@Override
	public void tick() {
		if (!first) {
			first = true;
			firstTick();
		}
		if (level.isClientSide()) {
			tickClient();
		} else {
			tickServer();
		}
	}
	
	protected void firstTick() {
	}
	
	protected void tickServer() {
	}
	
	protected void tickClient() {
	}
	
}

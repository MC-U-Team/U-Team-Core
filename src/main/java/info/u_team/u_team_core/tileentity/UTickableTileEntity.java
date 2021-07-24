package info.u_team.u_team_core.tileentity;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class UTickableTileEntity extends UTileEntity implements ITickableTileEntity {
	
	private boolean first;
	
	public UTickableTileEntity(TileEntityType<?> type) {
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

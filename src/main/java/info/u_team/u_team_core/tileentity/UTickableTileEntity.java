package info.u_team.u_team_core.tileentity;

import net.minecraft.tileentity.*;

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
		if (world.isRemote()) {
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

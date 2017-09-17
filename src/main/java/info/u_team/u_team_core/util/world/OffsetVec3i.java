package info.u_team.u_team_core.util.world;

import net.minecraft.util.math.*;

public class OffsetVec3i extends Vec3i {
	
	public OffsetVec3i(int x, int y, int z) {
		super(x, y, z);
	}
	
	public OffsetVec3i(Vec3i pos) {
		this(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public Vec3i offset(Vec3i vec) {
		return new Vec3i(this.getX() + vec.getX(), this.getY() + vec.getY(), this.getZ() + vec.getZ());
	}
	
}

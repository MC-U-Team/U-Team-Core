package info.u_team.u_team_core.schematic;

import net.minecraft.util.*;

public enum Rotation {
	
	ROTATION_0,
	ROTATION_90,
	ROTATION_180,
	ROTATION_270;
	
	public Rotation add(Rotation rotation) {
		switch (rotation) {
		case ROTATION_180:
			
			switch (this) {
			case ROTATION_0:
				return ROTATION_180;
			case ROTATION_90:
				return ROTATION_270;
			case ROTATION_180:
				return ROTATION_0;
			case ROTATION_270:
				return ROTATION_90;
			}
			
		case ROTATION_270:
			
			switch (this) {
			case ROTATION_0:
				return ROTATION_270;
			case ROTATION_90:
				return ROTATION_0;
			case ROTATION_180:
				return ROTATION_90;
			case ROTATION_270:
				return ROTATION_180;
			}
			
		case ROTATION_90:
			
			switch (this) {
			case ROTATION_0:
				return ROTATION_90;
			case ROTATION_90:
				return ROTATION_180;
			case ROTATION_180:
				return ROTATION_270;
			case ROTATION_270:
				return ROTATION_0;
			}
			
		default:
			return this;
		}
	}
	
	public EnumFacing rotate(EnumFacing facing) {
		if (facing.getAxis() == EnumFacing.Axis.Y) {
			return facing;
		} else {
			switch (this) {
			case ROTATION_90:
				return facing.rotateY();
			case ROTATION_180:
				return facing.getOpposite();
			case ROTATION_270:
				return facing.rotateYCCW();
			default:
				return facing;
			}
		}
	}
	
	public int rotate(int p_185833_1_, int p_185833_2_) {
		switch (this) {
		case ROTATION_90:
			return (p_185833_1_ + p_185833_2_ / 4) % p_185833_2_;
		case ROTATION_180:
			return (p_185833_1_ + p_185833_2_ / 2) % p_185833_2_;
		case ROTATION_270:
			return (p_185833_1_ + p_185833_2_ * 3 / 4) % p_185833_2_;
		default:
			return p_185833_1_;
		}
	}
}
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
}
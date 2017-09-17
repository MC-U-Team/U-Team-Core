package info.u_team.u_team_core.schematic;

import net.minecraft.util.math.Vec3i;

public class SchematicRegion {
	
	private Vec3i min, max;
	private int sizex, sizey, sizez, count;
	
	public SchematicRegion(Vec3i pos1, Vec3i pos2) {
		min = new Vec3i(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
		max = new Vec3i(Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));
		sizex = max.getX() - min.getX() + 1;
		sizey = max.getY() - min.getY() + 1;
		sizez = max.getZ() - min.getZ() + 1;
		count = sizex * sizey * sizez;
	}
	
	public Vec3i getMin() {
		return min;
	}
	
	public Vec3i getMax() {
		return max;
	}
	
	public int getSizeX() {
		return sizex;
	}
	
	public int getSizeY() {
		return sizey;
	}
	
	public int getSizeZ() {
		return sizez;
	}
	
	public int getCount() {
		return count;
	}
	
}

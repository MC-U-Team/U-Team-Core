package info.u_team.u_team_core.util;

import net.minecraft.util.math.Vec3d;

public class MathUtil {
	
	public static Vec3d rotateVectorAroundYCC(Vec3d vec, double angle) {
		return rotateVectorCC(vec, new Vec3d(0, 1, 0), angle);
	}
	
	public static Vec3d rotateVectorCC(Vec3d vec, Vec3d axis, double angle) {
		final double x = vec.getX();
		final double y = vec.getY();
		final double z = vec.getZ();
		
		final double u = axis.getX();
		final double v = axis.getY();
		final double w = axis.getZ();
		
		final double rotationX = u * (u * x + v * y + w * z) * (1 - Math.cos(angle)) + x * Math.cos(angle) + (-w * y + v * z) * Math.sin(angle);
		final double rotationY = v * (u * x + v * y + w * z) * (1 - Math.cos(angle)) + y * Math.cos(angle) + (w * x - u * z) * Math.sin(angle);
		final double rotationZ = w * (u * x + v * y + w * z) * (1 - Math.cos(angle)) + z * Math.cos(angle) + (-v * x + u * y) * Math.sin(angle);
		return new Vec3d(rotationX, rotationY, rotationZ);
	}
	
}

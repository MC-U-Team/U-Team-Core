package info.u_team.u_team_core.util;

import java.util.Random;

import net.minecraft.util.math.*;

public class MathUtil {
	
	public static final Random RANDOM = new Random();
	
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
	
	@Deprecated
	public static int getRandomNumberInRange(int min, int max) {
		return randomNumberInRange(min, max);
	}
	
	@Deprecated
	public static int getRandomNumberInRange(Random random, int min, int max) {
		return randomNumberInRange(random, min, max);
	}
	
	@Deprecated
	public static float getRandomNumberInRange(float min, float max) {
		return randomNumberInRange(min, max);
	}
	
	@Deprecated
	public static float getRandomNumberInRange(Random random, float min, float max) {
		return randomNumberInRange(random, min, max);
	}
	
	@Deprecated
	public static double getRandomNumberInRange(double min, double max) {
		return randomNumberInRange(min, max);
	}
	
	@Deprecated
	public static double getRandomNumberInRange(Random random, double min, double max) {
		return randomNumberInRange(random, min, max);
	}
	
	/**
	 * Returns a pseudo random number in range of min and max (inclusive). Use this {@link #RANDOM} instance
	 * 
	 * @param min Minimal value (inclusive)
	 * @param max Maximal value (inclusive)
	 * @return Return a random value between min and max
	 */
	public static int randomNumberInRange(int min, int max) {
		return randomNumberInRange(RANDOM, min, max);
	}
	
	/**
	 * Returns a pseudo random number in range of min and max (inclusive).
	 * 
	 * @param random The random instance
	 * @param min Minimal value (inclusive)
	 * @param max Maximal value (inclusive)
	 * @return Return a random value between min and max
	 */
	public static int randomNumberInRange(Random random, int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}
	
	/**
	 * Returns a pseudo random number in range of min and max (inclusive). Use this {@link #RANDOM} instance
	 * 
	 * @param min Minimal value (inclusive)
	 * @param max Maximal value (inclusive)
	 * @return Return a random value between min and max
	 */
	public static float randomNumberInRange(float min, float max) {
		return randomNumberInRange(RANDOM, min, max);
	}
	
	/**
	 * Returns a pseudo random number in range of min and max (inclusive).
	 * 
	 * @param random The random instance
	 * @param min Minimal value (inclusive)
	 * @param max Maximal value (inclusive)
	 * @return Return a random value between min and max
	 */
	public static float randomNumberInRange(Random random, float min, float max) {
		return random.nextFloat() * (max - min) + min;
	}
	
	/**
	 * Returns a pseudo random number in range of min and max (inclusive). Use this {@link #RANDOM} instance
	 * 
	 * @param min Minimal value (inclusive)
	 * @param max Maximal value (inclusive)
	 * @return Return a random value between min and max
	 */
	public static double randomNumberInRange(double min, double max) {
		return randomNumberInRange(RANDOM, min, max);
	}
	
	/**
	 * Returns a pseudo random number in range of min and max (inclusive).
	 * 
	 * @param random The random instance
	 * @param min Minimal value (inclusive)
	 * @param max Maximal value (inclusive)
	 * @return Return a random value between min and max
	 */
	public static double randomNumberInRange(Random random, double min, double max) {
		return random.nextDouble() * (max - min) + min;
	}
	
	/**
	 * Returns a value between min and max
	 * 
	 * @param min Minimal value (inclusive)
	 * @param max Maximal value (inclusive)
	 * @param value Value that should be in range
	 * @return Return a value between min and max
	 */
	public static int valueInRange(int min, int max, int value) {
		return Math.min(max, Math.max(min, value));
	}
	
	/**
	 * Returns a value between min and max
	 * 
	 * @param min Minimal value (inclusive)
	 * @param max Maximal value (inclusive)
	 * @param value Value that should be in range
	 * @return Return a value between min and max
	 */
	public static long valueInRange(long min, long max, long value) {
		return Math.min(max, Math.max(min, value));
	}
	
	/**
	 * Returns a value between min and max
	 * 
	 * @param min Minimal value (inclusive)
	 * @param max Maximal value (inclusive)
	 * @param value Value that should be in range
	 * @return Return a value between min and max
	 */
	public static float valueInRange(float min, float max, float value) {
		return Math.min(max, Math.max(min, value));
	}
	
	/**
	 * Returns a value between min and max
	 * 
	 * @param min Minimal value (inclusive)
	 * @param max Maximal value (inclusive)
	 * @param value Value that should be in range
	 * @return Return a value between min and max
	 */
	public static double valueInRange(double min, double max, double value) {
		return Math.min(max, Math.max(min, value));
	}
	
	/**
	 * Returns the distance between x1, z1 and x2, z2
	 * 
	 * @param x1 First x coordinate
	 * @param z1 First z coordinate
	 * @param x2 Second x coordinate
	 * @param z2 Second z coordinate
	 * @return The distance between these two coordinates
	 */
	public static float getPlaneDistance(int x1, int z1, int x2, int z2) {
		int xDiff = x2 - x1;
		int zDiff = z2 - z1;
		return MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
	}
	
}

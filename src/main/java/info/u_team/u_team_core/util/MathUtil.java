/*-*****************************************************************************
 * Copyright 2018 U-Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package info.u_team.u_team_core.util;

import java.util.Random;

import net.minecraft.util.math.*;

/**
 * Math utility
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public class MathUtil {
	
	public static int distance(int a, int b) {
		return Math.abs(Math.abs(a) - Math.abs(b));
	}
	
	public static double getDistanceAtoB(double x1, double z1, double x2, double z2) {
		double dx = x1 - x2;
		double dz = z1 - z2;
		return Math.sqrt((dx * dx + dz * dz));
	}
	
	public static Vec3i getMinVec(Vec3i v1, Vec3i v2) {
		int x = Math.min(v1.getX(), v2.getX());
		int y = Math.min(v1.getY(), v2.getY());
		int z = Math.min(v1.getZ(), v2.getZ());
		return new Vec3i(x, y, z);
	}
	
	public static Vec3i getMaxVec(Vec3i v1, Vec3i v2) {
		int x = Math.max(v1.getX(), v2.getX());
		int y = Math.max(v1.getY(), v2.getY());
		int z = Math.max(v1.getZ(), v2.getZ());
		return new Vec3i(x, y, z);
	}
	
	public static int getRandomNumberInRange(int min, int max) {
		return getRandomNumberInRange(new Random(), min, max);
	}
	
	public static float getRandomNumberInRange(float min, float max) {
		return getRandomNumberInRange(new Random(), min, max);
	}
	
	public static double getRandomNumberInRange(double min, double max) {
		return getRandomNumberInRange(new Random(), min, max);
	}
	
	public static int getRandomNumberInRange(Random rand, int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}
	
	public static float getRandomNumberInRange(Random rand, float min, float max) {
		return rand.nextFloat() * (max - min) + min;
	}
	
	public static double getRandomNumberInRange(Random rand, double min, double max) {
		return rand.nextDouble() * (max - min) + min;
	}
	
	public static double getAverage(long... numbers) {
		return MathHelper.average(numbers);
	}
	
	public static boolean isInRange(int number, int min, int max) {
		return number >= min && number <= max;
	}
	
}

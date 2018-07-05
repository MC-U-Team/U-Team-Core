/*******************************************************************************
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
package info.u_team.u_team_core.util.world;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.*;

/**
 * World utility
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public class WorldUtil {
	
	public static RayTraceResult rayTraceServerSide(EntityPlayer player, double range) {
		Vec3d vec3 = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		Vec3d vec31 = player.getLookVec();
		Vec3d vec32 = vec3.addVector(vec31.x * range, vec31.y * range, vec31.z * range);
		return player.world.rayTraceBlocks(vec3, vec32, true, false, true);
	}
}

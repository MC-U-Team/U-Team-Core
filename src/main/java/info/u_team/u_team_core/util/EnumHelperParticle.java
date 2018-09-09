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

import javax.annotation.Nullable;

import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Extended EnumHelper that support adding custom particles to minecraft
 * 
 * @author HyCraftHD
 * @date 09.09.2018
 */
public class EnumHelperParticle extends EnumHelper {
	
	private static Class<?>[][] clientTypes = { { EnumParticleTypes.class, String.class, int.class, boolean.class, int.class } };
	
	@Nullable
	public static EnumParticleTypes addParticle(String name, int id, boolean ignoreRange) {
		return addEnum(EnumParticleTypes.class, name, name, id, ignoreRange, 0);
	}
	
	@Nullable
	public static EnumParticleTypes addParticle(String name, int id, boolean ignoreRange, int arguments) {
		return addEnum(EnumParticleTypes.class, name, name, id, ignoreRange, arguments);
	}
	
	private static <T extends Enum<?>> T addEnum(Class<T> enumType, String enumName, Object... paramValues) {
		return addEnum(clientTypes, enumType, enumName, paramValues);
	}
	
}

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

import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Extended EnumHelper that support adding custom sound categories to minecraft
 * 
 * @author HyCraftHD
 * @date 05.10.2018
 */
public class EnumHelperSoundCategory extends EnumHelper {
	
	private static Class<?>[][] clientTypes = { { SoundCategory.class, String.class } };
	
	public static SoundCategory addSoundCategory(String name) {
		SoundCategory category = addEnum(SoundCategory.class, name, name);
		SoundCategory.SOUND_CATEGORIES.put(category.getName(), category);
		return category;
	}
	
	private static <T extends Enum<?>> T addEnum(Class<T> enumType, String enumName, Object... paramValues) {
		return addEnum(clientTypes, enumType, enumName, paramValues);
	}
	
}

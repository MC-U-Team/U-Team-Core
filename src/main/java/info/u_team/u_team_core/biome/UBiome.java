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
package info.u_team.u_team_core.biome;

import info.u_team.u_team_core.api.registry.IUBiome;
import net.minecraft.world.biome.Biome;

/**
 * Biome API<br>
 * -> Basic Biome
 * 
 * @date 05.07.2018
 * @author HyCraftHD
 */
public class UBiome extends Biome implements IUBiome {
	
	protected String name;
	
	public UBiome(String name) {
		super(new BiomeProperties(name));
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}

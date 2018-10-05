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

package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.api.registry.IUBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Biome API<br>
 * -> Registry
 * 
 * @date 05.07.2018
 * @author HyCraftHD
 */
public class BiomeRegistry {
	
	static List<Biome> biomes = new ArrayList<>();
	
	public static void register(String modid, Biome biome) {
		if (biome instanceof IUBiome) {
			IUBiome iubiome = (IUBiome) biome;
			biome.setRegistryName(modid, iubiome.getName());
		}
		biomes.add(biome);
	}
	
	public static void register(String modid, Collection<Biome> list) {
		list.forEach(block -> register(modid, block));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<Biome> event) {
		IForgeRegistry<Biome> registry = event.getRegistry();
		biomes.forEach(registry::register);
	}
	
}

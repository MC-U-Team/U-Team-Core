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
package info.u_team.u_team_core.registry;

import static info.u_team.u_team_core.registry.BlockRegistry.blocks;
import static info.u_team.u_team_core.registry.ItemRegistry.items;

import info.u_team.u_team_core.api.IModelProvider;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;

/**
 * Model API<br>
 * -> Registry
 * 
 * @date 05.07.2018
 * @author HyCraftHD
 */

@SideOnly(Side.CLIENT)
public class ModelRegistry {
	
	@SubscribeEvent
	public static void event(ModelRegistryEvent event) {
		blocks.stream().filter(block -> block instanceof IModelProvider).map(block -> (IModelProvider) block).forEach(block -> block.registerModel());
		items.stream().filter(item -> item instanceof IModelProvider).map(item -> (IModelProvider) item).forEach(item -> item.registerModel());
	}
	
}

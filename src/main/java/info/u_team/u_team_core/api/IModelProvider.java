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
package info.u_team.u_team_core.api;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.*;

/**
 * Models can now be registered directly in items with this interface
 * 
 * @author HyCraftHD
 * @date 24.06.2018
 */
public interface IModelProvider {
	
	@SideOnly(Side.CLIENT)
	public void registerModel();
	
	@SideOnly(Side.CLIENT)
	public default void setModel(Item item, int metadata, ResourceLocation location) {
		ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(location, "inventory"));
	}
	
}

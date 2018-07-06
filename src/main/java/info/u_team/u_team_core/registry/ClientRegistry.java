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

import info.u_team.u_team_core.util.stack.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Client registry
 * 
 * @author HyCraftHD
 * @date 23.10.2017
 */
@SideOnly(Side.CLIENT)
public class ClientRegistry {
	
	public static <T extends Entity> void registerEntityRenderer(Class<T> clazz, IRenderFactory<? super T> rendererFactory) {
		RenderingRegistry.registerEntityRenderingHandler(clazz, rendererFactory);
	}
	
	public static void registerKeybinding(KeyBinding key) {
		net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(key);
	}
	
	public static void registerModel(Item item) {
		registerModel(item, 0);
	}
	
	public static void registerModel(Block block) {
		registerModel(block, 0);
	}
	
	public static void registerModel(Item item, int meta) {
		registerModel(item, meta, new ModelResourceLocation(ItemUtil.getRegistryName(item), "inventory"));
	}
	
	public static void registerModel(Block block, int meta) {
		registerModel(block, meta, new ModelResourceLocation(BlockUtil.getRegistryName(block), "inventory"));
	}
	
	public static void registerModel(Item item, int meta, ModelResourceLocation location) {
		ModelLoader.setCustomModelResourceLocation(item, meta, location);
	}
	
	public static void registerModel(Block block, int meta, ModelResourceLocation location) {
		registerModel(Item.getItemFromBlock(block), meta, location);
	}
	
	public static <T extends TileEntity> void registerSpecialTileEntityRenderer(Class<T> clazz, TileEntitySpecialRenderer<? super T> renderer) {
		net.minecraftforge.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer(clazz, renderer);
	}
	
}

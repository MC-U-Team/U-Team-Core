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

package info.u_team.u_team_core.tileentity;

import info.u_team.u_team_core.UCoreConstants;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * TileEntity API<br>
 * -> TileEntity Holder
 * 
 * @author HyCraftHD
 * @date 21.01.2018
 */
public class UTileEntityProvider {
	
	private Class<? extends TileEntity> tileentity;
	private Object objects[];
	
	public UTileEntityProvider(ResourceLocation location, Class<? extends TileEntity> tileentity, Object... objects) {
		this(location, true, tileentity, objects);
	}
	
	public UTileEntityProvider(ResourceLocation location, boolean shouldRegister, Class<? extends TileEntity> tileentity, Object... objects) {
		this.tileentity = tileentity;
		this.objects = objects;
		
		if (shouldRegister) {
			GameRegistry.registerTileEntity(tileentity, location);
		}
	}
	
	public TileEntity create(World world, int meta) {
		try {
			if (objects == null || objects.length == 0) {
				return tileentity.newInstance();
			} else {
				Class<?>[] classes = new Class<?>[objects.length];
				int i = 0;
				for (Object object : objects) {
					classes[i] = object.getClass();
					i++;
				}
				tileentity.getConstructor(classes).newInstance(objects);
			}
		} catch (Exception ex) {
			UCoreConstants.LOGGER.error("Couldn't create tileentity object.", ex);
		}
		return null;
	}
}

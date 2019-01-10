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

package info.u_team.u_team_core.gui;

import info.u_team.u_team_core.container.UContainerTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.*;

/**
 * Gui API<br>
 * -> Gui Container TileEntity
 * 
 * @date 04.01.2019
 * @author HyCraftHD
 */
@SideOnly(Side.CLIENT)
public class UGuiContainerTileEntity extends UGuiContainer {
	
	protected final TileEntity tileentity;
	
	protected boolean handleNextSync;
	
	public UGuiContainerTileEntity(UContainerTileEntity container, ResourceLocation background) {
		super(container, background);
		tileentity = container.getTileentity();
		handleNextSync = true;
	}
	
	// This might be buggy cause initGui might not have run already
	public void handleServerDataInstant(NBTTagCompound compound) {
	}
	
	public void handleServerData(NBTTagCompound compound) {
		if (handleNextSync) {
			handleNextSync = false;
			handleServerDataOnFirstArrival(compound);
		}
	}
	
	public void handleServerDataOnFirstArrival(NBTTagCompound compound) {
	}
	
}

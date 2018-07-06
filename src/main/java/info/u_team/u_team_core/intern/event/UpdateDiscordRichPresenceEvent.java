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

package info.u_team.u_team_core.intern.event;

import info.u_team.u_team_core.intern.discord.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;

/**
 * Internal Event for update discord rich presence
 * 
 * @author HyCraftHD
 * @date 24.03.2018
 */
@SideOnly(Side.CLIENT)
public class UpdateDiscordRichPresenceEvent {
	
	@SubscribeEvent
	public void on(InitGuiEvent.Pre event) {
		if (event.getGui() instanceof GuiMainMenu || event.getGui() instanceof GuiWorldSelection || event.getGui() instanceof GuiMultiplayer) {
			DiscordRichPresence.setIdling();
		}
	}
	
	@SubscribeEvent
	public void on(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityPlayerSP) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if (player.getUniqueID().equals(Minecraft.getMinecraft().player.getUniqueID())) {
				DiscordRichPresence.setDimension(player.getEntityWorld().provider);
			}
		}
	}
}

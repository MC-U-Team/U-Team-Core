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

import info.u_team.u_team_core.UCoreConstants;
import info.u_team.u_team_core.intern.config.ClientConfig;
import info.u_team.u_team_core.intern.discord.DiscordRichPresence;
import info.u_team.u_team_core.intern.discord.DiscordRichPresence.*;
import net.minecraft.client.Minecraft;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;

/**
 * Internal Event for config sync with gui and file and annotation system
 * 
 * @author HyCraftHD
 * @date 08.09.2018
 */
@SideOnly(Side.CLIENT)
public class EventHandlerConfigChange {
	
	@SubscribeEvent
	public static void on(OnConfigChangedEvent event) {
		if (event.getModID().equals(UCoreConstants.MODID)) {
			ConfigManager.sync(UCoreConstants.MODID, Type.INSTANCE);
			checkDiscord();
		}
	}
	
	private static void checkDiscord() {
		if (!ClientConfig.discord.discord_richpresence && DiscordRichPresence.isEnabled()) {
			DiscordRichPresence.stop();
		} else if (ClientConfig.discord.discord_richpresence && !DiscordRichPresence.isEnabled()) {
			Minecraft minecraft = Minecraft.getMinecraft();
			State state;
			if (minecraft.world != null) {
				WorldProvider provider = minecraft.world.provider;
				state = DiscordRichPresence.getStateFromDimension(provider);
			} else {
				state = new State(EnumState.MENU);
			}
			DiscordRichPresence.current = state;
			DiscordRichPresence.start();
		}
	}
}
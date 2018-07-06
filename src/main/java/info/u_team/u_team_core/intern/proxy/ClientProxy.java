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

package info.u_team.u_team_core.intern.proxy;

import info.u_team.u_team_core.intern.config.Config;
import info.u_team.u_team_core.intern.discord.DiscordRichPresence;
import info.u_team.u_team_core.intern.event.UpdateDiscordRichPresenceEvent;
import info.u_team.u_team_core.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * This class has methods that are only run by the client. To run common things
 * you need to run super method of {@link CommonProxy}
 * 
 * @author HyCraftHD
 * @date 16.08.2017
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	@Override
	public void preinit(FMLPreInitializationEvent event) {
		super.preinit(event);
		CommonRegistry.registerEventHandler(ModelRegistry.class);
		
		Config.init(event.getSuggestedConfigurationFile());
		
		if (Config.getDiscordRichPresenceEnabled()) {
			DiscordRichPresence.start();
		}
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		if (Config.getDiscordRichPresenceEnabled()) {
			MinecraftForge.EVENT_BUS.register(new UpdateDiscordRichPresenceEvent());
		}
	}
	
	@Override
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}
	
	@Override
	public void serverstart(FMLServerStartingEvent event) {
		super.serverstart(event);
	}
	
}

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
package info.u_team.u_team_core.intern.config;

import java.io.File;

import net.minecraftforge.common.config.*;

/**
 * This class manages our config file
 * 
 * @author HyCraftHD
 * @date 28.05.2018
 */
public class Config {
	
	private static Configuration configuration;
	
	private static Property discord_enablerichpresence;
	
	public static void init(File file) {
		configuration = new Configuration(file);
		configuration.load();
		
		discord_enablerichpresence = configuration.get("discord", "EnableRichPresence", true, "If you have discord installed, then it's cool to show some details about your game ;)");
		
		configuration.save();
	}
	
	public static boolean getDiscordRichPresenceEnabled() {
		return discord_enablerichpresence.getBoolean();
	}
	
}

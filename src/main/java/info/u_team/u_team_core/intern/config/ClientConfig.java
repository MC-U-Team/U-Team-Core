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

package info.u_team.u_team_core.intern.config;

import info.u_team.u_team_core.UCoreConstants;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;

/**
 * This class manages our config file on the client
 * 
 * @author HyCraftHD
 * @date 08.09.2018
 */
@Config(modid = UCoreConstants.MODID, name = UCoreConstants.MODID + "/client", category = "")
public class ClientConfig {
	
	public static Discord discord = new Discord();
	
	public static class Discord {
		
		@Comment("If you have discord installed it will show your some details about your game as rich presence")
		@Name("Discord Rich Presence")
		public boolean discord_richpresence = true;
		
	}
	
}

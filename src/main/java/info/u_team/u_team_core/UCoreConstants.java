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

package info.u_team.u_team_core;

import org.apache.logging.log4j.*;

/**
 * This class defines important constants
 * 
 * @author HyCraftHD
 * @date 16.08.2017
 */
public final class UCoreConstants {
	
	public static final String MODID = "uteamcore";
	public static final String NAME = "UTeam Core";
	public static final String VERSION = "${version}"; // Version is replaced when compiling
	public static final String MCVERSION = "${mcversion}"; // Version is replaced when compiling
	public static final String DEPENDENCIES = "required:forge@[14.23.4.2705,)";
	public static final String UPDATEURL = "https://api.u-team.info/update/uteamcore.json";
	
	public static final String COMMONPROXY = "info.u_team.u_team_core.intern.proxy.CommonProxy";
	public static final String CLIENTPROXY = "info.u_team.u_team_core.intern.proxy.ClientProxy";
	
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	private UCoreConstants() { // Let this class never be initialized
	}
	
}

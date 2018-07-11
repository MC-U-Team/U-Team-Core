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

import info.u_team.u_team_core.registry.*;
import net.minecraftforge.fml.common.event.*;

/**
 * This class has methods that are only run by the server.
 * 
 * @author HyCraftHD
 * @date 16.08.2017
 */
public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		CommonRegistry.registerEventHandler(BlockRegistry.class, ItemRegistry.class, EntityRegistry.class, BiomeRegistry.class);
	}
	
	public void init(FMLInitializationEvent event) {
	}
	
	public void postinit(FMLPostInitializationEvent event) {
	}
	
	public void serverstart(FMLServerStartingEvent event) {
	}
	
}

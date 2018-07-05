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
package info.u_team.u_team_core;

import static info.u_team.u_team_core.UCoreConstants.*;

import info.u_team.u_team_core.intern.proxy.CommonProxy;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;

/**
 * This is the main class of this utility mod and represents the mod in forge.
 * It is used to listen to events and setup everything nessesary.
 * 
 * @author HyCraftHD
 * @date 16.08.2017
 */
@Mod(modid = MODID, name = NAME, version = VERSION, acceptedMinecraftVersions = MCVERSION, dependencies = DEPENDENCIES, updateJSON = UPDATEURL)
public class UCoreMain {
	
	// instance setup and other variables
	
	private UCoreMain() { // Let this class only have one instance (singleton)
	}
	
	private static UCoreMain instance = new UCoreMain();
	
	@SidedProxy(serverSide = COMMONPROXY, clientSide = CLIENTPROXY)
	private static CommonProxy proxy;
	
	// getter and setter
	
	@InstanceFactory
	public static UCoreMain getInstance() {
		return instance;
	}
	
	public CommonProxy getProxy() {
		return proxy;
	}
	
	// fml events
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		proxy.preinit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		proxy.postinit(event);
	}
	
	@EventHandler
	public void serverstart(FMLServerStartingEvent event) {
		proxy.serverstart(event);
	}
}

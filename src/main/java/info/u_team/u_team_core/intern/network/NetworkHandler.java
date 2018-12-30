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

package info.u_team.u_team_core.intern.network;

import info.u_team.u_team_core.intern.network.message.MessageSyncedContainer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Internal class for registering custom netty messages
 * 
 * @date 30.12.2018
 * @author HyCraftHD
 */

public class NetworkHandler {
	
	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel("uteamcore"); // Don't use too long channel names. I used to spend hours on finding the bug...
	
	public static void init() {
		network.registerMessage(MessageSyncedContainer.Handler.class, MessageSyncedContainer.class, 1, Side.CLIENT);
		network.registerMessage(MessageSyncedContainer.Handler.class, MessageSyncedContainer.class, 1, Side.SERVER);
	}
	
}

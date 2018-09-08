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

package info.u_team.u_team_core.intern.command;

import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.*;

/**
 * Little useful command on servers to check your ping. It's using the players
 * ping variable from minecraft which is not really accurate but can be used as
 * a reference
 * 
 * @author HyCraftHD
 * @date 09.09.2018
 */
public class CommandPing extends CommandBase {
	
	@Override
	public String getName() {
		return "ping";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "/ping";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (!(sender instanceof EntityPlayerMP)) {
			sender.sendMessage(new TextComponentTranslation("uteamcore:command.onlyplayer"));
			return;
		}
		EntityPlayerMP player = (EntityPlayerMP) sender;
		player.sendMessage(new TextComponentTranslation("uteamcore:command.ping", player.ping));
	}
	
}

package info.u_team.u_team_core.intern.command;

import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.*;

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

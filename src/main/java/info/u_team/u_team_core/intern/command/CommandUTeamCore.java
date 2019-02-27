package info.u_team.u_team_core.intern.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import info.u_team.u_team_core.intern.command.uteamcore.SubCommandUTeamCoreDumbItemStack;
import net.minecraft.command.CommandSource;

public class CommandUTeamCore {
	
	public CommandUTeamCore(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(LiteralArgumentBuilder.<CommandSource> literal("uteamcore").then(SubCommandUTeamCoreDumbItemStack.register()));
	}
	
}

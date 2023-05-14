package info.u_team.u_team_core.intern.init;

import com.mojang.brigadier.CommandDispatcher;

import info.u_team.u_team_core.intern.command.UTeamCoreCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class UCoreCommands {
	
	private static void onRegisterCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
		UTeamCoreCommand.register(dispatcher);
	}
	
	public static void register() {
		CommandRegistrationCallback.EVENT.register(UCoreCommands::onRegisterCommands);
	}
}

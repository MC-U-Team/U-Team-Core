package info.u_team.u_team_core.intern.command;

import com.mojang.brigadier.CommandDispatcher;

import info.u_team.u_team_core.intern.command.uteamcore.DimensionTeleportSubCommand;
import info.u_team.u_team_core.intern.command.uteamcore.ItemStackInfoSubCommand;
import info.u_team.u_team_core.intern.command.uteamcore.LocateBiomeSubCommand;
import info.u_team.u_team_core.intern.command.uteamcore.LocateStructureSubCommand;
import info.u_team.u_team_core.intern.command.uteamcore.PingSubCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class UTeamCoreCommand {
	
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("uteamcore") //
				.then(ItemStackInfoSubCommand.register()) //
				.then(DimensionTeleportSubCommand.register()) //
				.then(dispatcher.register(PingSubCommand.register())) //
				.then(LocateBiomeSubCommand.register()) //
				.then(LocateStructureSubCommand.register()));
	}
	
}

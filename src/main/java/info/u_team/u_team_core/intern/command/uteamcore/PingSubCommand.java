package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.*;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class PingSubCommand {
	
	public static LiteralArgumentBuilder<CommandSource> register() {
		return Commands.literal("ping") //
				.executes(context -> execute(context.getSource())) //
				.requires(source -> source.hasPermissionLevel(2)) //
				.then(Commands.argument("target", EntityArgument.player()) //
						.executes(context -> execute(context.getSource(), EntityArgument.getPlayer(context, "target"))));
	}
	
	private static int execute(CommandSource source) throws CommandSyntaxException {
		source.sendFeedback(new StringTextComponent("Your ping is " + source.asPlayer().ping), false);
		return 0;
	}
	
	private static int execute(CommandSource source, ServerPlayerEntity player) {
		source.sendFeedback(new StringTextComponent("The ping of player " + player.getName().getFormattedText() + " is " + player.ping), false);
		return 0;
	}
	
}

package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class PingSubCommand {
	
	private static final String SUCCESS_TRANSLATION_STRING = "commands.uteamcore.ping.success.";
	
	public static LiteralArgumentBuilder<CommandSourceStack> register() {
		return Commands.literal("ping") //
				.executes(context -> execute(context.getSource())) //
				.requires(source -> source.hasPermission(2)) //
				.then(Commands.argument("target", EntityArgument.player()) //
						.executes(context -> execute(context.getSource(), EntityArgument.getPlayer(context, "target"))));
	}
	
	private static int execute(CommandSourceStack source) throws CommandSyntaxException {
		source.sendSuccess(Component.translatable(SUCCESS_TRANSLATION_STRING + "self", Component.literal(String.valueOf(source.getPlayerOrException().latency)).withStyle(ChatFormatting.GOLD)), false);
		return 0;
	}
	
	private static int execute(CommandSourceStack source, ServerPlayer player) {
		source.sendSuccess(Component.translatable(SUCCESS_TRANSLATION_STRING + "other", player.getName(), Component.literal(String.valueOf(player.latency)).withStyle(ChatFormatting.GOLD)), false);
		return 0;
	}
	
}

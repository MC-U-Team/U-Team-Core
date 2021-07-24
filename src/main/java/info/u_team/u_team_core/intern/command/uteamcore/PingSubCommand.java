package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class PingSubCommand {
	
	private static final String SUCCESS_TRANSLATION_STRING = "commands.uteamcore.ping.success.";
	
	public static LiteralArgumentBuilder<CommandSource> register() {
		return Commands.literal("ping") //
				.executes(context -> execute(context.getSource())) //
				.requires(source -> source.hasPermission(2)) //
				.then(Commands.argument("target", EntityArgument.player()) //
						.executes(context -> execute(context.getSource(), EntityArgument.getPlayer(context, "target"))));
	}
	
	private static int execute(CommandSource source) throws CommandSyntaxException {
		source.sendSuccess(new TranslationTextComponent(SUCCESS_TRANSLATION_STRING + "self", new StringTextComponent(String.valueOf(source.getPlayerOrException().latency)).withStyle(TextFormatting.GOLD)), false);
		return 0;
	}
	
	private static int execute(CommandSource source, ServerPlayerEntity player) {
		source.sendSuccess(new TranslationTextComponent(SUCCESS_TRANSLATION_STRING + "other", player.getName(), new StringTextComponent(String.valueOf(player.latency)).withStyle(TextFormatting.GOLD)), false);
		return 0;
	}
	
}

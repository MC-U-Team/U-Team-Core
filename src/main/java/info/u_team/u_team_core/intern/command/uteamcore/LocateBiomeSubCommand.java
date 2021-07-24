package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.server.commands.LocateBiomeCommand;
import net.minecraft.resources.ResourceLocation;

public class LocateBiomeSubCommand {
	
	public static ArgumentBuilder<CommandSourceStack, ?> register() {
		return Commands.literal("locatebiome") //
				.requires(source -> source.hasPermission(2)) //
				.then(Commands.argument("biome", ResourceLocationArgument.id()) //
						.suggests(SuggestionProviders.AVAILABLE_BIOMES) //
						.executes(context -> locateBiome(context.getSource(), context.getArgument("biome", ResourceLocation.class))));
	}
	
	private static int locateBiome(CommandSourceStack source, ResourceLocation biomeRegistryName) throws CommandSyntaxException {
		return LocateBiomeCommand.locateBiome(source, biomeRegistryName);
	}
}

package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.command.impl.LocateBiomeCommand;
import net.minecraft.util.ResourceLocation;

public class LocateBiomeSubCommand {
	
	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("locatebiome") //
				.requires(source -> source.hasPermission(2)) //
				.then(Commands.argument("biome", ResourceLocationArgument.id()) //
						.suggests(SuggestionProviders.AVAILABLE_BIOMES) //
						.executes(context -> locateBiome(context.getSource(), context.getArgument("biome", ResourceLocation.class))));
	}
	
	private static int locateBiome(CommandSource source, ResourceLocation biomeRegistryName) throws CommandSyntaxException {
		return LocateBiomeCommand.locateBiome(source, biomeRegistryName);
	}
}

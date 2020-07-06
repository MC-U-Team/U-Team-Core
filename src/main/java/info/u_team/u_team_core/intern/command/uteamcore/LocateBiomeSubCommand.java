package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.*;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.impl.LocateBiomeCommand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class LocateBiomeSubCommand {
	
	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("locatebiome") //
				.requires(source -> source.hasPermissionLevel(2)) //
				.then(Commands.argument("biome", ResourceLocationArgument.resourceLocation()) //
						.suggests((context, builder) -> ISuggestionProvider.suggest(ForgeRegistries.BIOMES.getKeys().stream().map(ResourceLocation::toString), builder)) //
						.executes(context -> locateBiome(context.getSource(), context.getArgument("biome", ResourceLocation.class))));
	}
	
	private static int locateBiome(CommandSource source, ResourceLocation biomeRegistryName) throws CommandSyntaxException {
		return LocateBiomeCommand.func_241049_a_(source, biomeRegistryName);
	}
}

package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.*;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.impl.LocateCommand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.registries.ForgeRegistries;

public class LocateStructureSubCommand {
	
	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("locatestructure") //
				.requires(source -> source.hasPermissionLevel(2)) //
				.then(Commands.argument("structure", ResourceLocationArgument.resourceLocation()) //
						.suggests((context, builder) -> ISuggestionProvider.suggest(ForgeRegistries.STRUCTURE_FEATURES.getKeys().stream().map(ResourceLocation::toString), builder)) //
						.executes(context -> locateStructure(context.getSource(), ForgeRegistries.STRUCTURE_FEATURES.getValue(context.getArgument("structure", ResourceLocation.class)))));
	}
	
	private static int locateStructure(CommandSource source, Structure<?> structure) throws CommandSyntaxException {
		return LocateCommand.func_241053_a_(source, structure);
	}
}

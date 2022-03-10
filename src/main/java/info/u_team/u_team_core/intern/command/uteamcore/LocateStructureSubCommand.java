package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.registries.ForgeRegistries;

public class LocateStructureSubCommand {
	
//	public static ArgumentBuilder<CommandSourceStack, ?> register() {
//		return Commands.literal("locatestructure") //
//				.requires(source -> source.hasPermission(2)) //
//				.then(Commands.argument("structure", ResourceLocationArgument.id()) //
//						.suggests((context, builder) -> SharedSuggestionProvider.suggest(ForgeRegistries.STRUCTURE_FEATURES.getKeys().stream().map(ResourceLocation::toString), builder)) //
//						.executes(context -> locateStructure(context.getSource(), ForgeRegistries.STRUCTURE_FEATURES.getValue(context.getArgument("structure", ResourceLocation.class)))));
//	}
//	
//	private static int locateStructure(CommandSourceStack source, StructureFeature<?> structure) throws CommandSyntaxException {
//		return LocateCommand.locate(source, structure);
//	}
}

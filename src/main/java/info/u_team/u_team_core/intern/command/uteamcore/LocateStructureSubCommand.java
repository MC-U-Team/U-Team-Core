package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.*;

import net.minecraft.command.*;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import net.minecraftforge.registries.GameData;

public class LocateStructureSubCommand {
	
	private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.locate.failed"));
	
	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("locatestructure") //
				.requires(source -> source.hasPermissionLevel(2)) //
				.then(Commands.argument("structure", ResourceLocationArgument.resourceLocation()) //
						.suggests((context, builder) -> ISuggestionProvider.suggest(GameData.getStructureFeatures().keySet().stream().map(ResourceLocation::toString), builder)) //
						.executes(context -> locateStructure(context.getSource(), context.getArgument("structure", ResourceLocation.class))));
	}
	
	private static int locateStructure(CommandSource source, ResourceLocation structureName) throws CommandSyntaxException {
		final BlockPos pos = new BlockPos(source.getPos());
		
		final BlockPos foundPos = source.getWorld().findNearestStructure(structureName.toString(), pos, 100, false);
		
		if (foundPos == null) {
			throw FAILED_EXCEPTION.create();
		}
		
		final int distance = MathHelper.floor(getDistance(pos.getX(), pos.getZ(), foundPos.getX(), foundPos.getZ()));
		
		final ITextComponent text = TextComponentUtils.wrapInSquareBrackets(new TranslationTextComponent("chat.coordinates", foundPos.getX(), "~", foundPos.getZ())).applyTextStyle((style) -> {
			style.setColor(TextFormatting.GREEN).setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + foundPos.getX() + " ~ " + foundPos.getZ())).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("chat.coordinates.tooltip")));
		});
		source.sendFeedback(new TranslationTextComponent("commands.locate.success", structureName, text, distance), false);
		
		return distance;
	}
	
	private static float getDistance(int x1, int z1, int x2, int z2) {
		int i = x2 - x1;
		int j = z2 - z1;
		return MathHelper.sqrt((float) (i * i + j * j));
	}
}

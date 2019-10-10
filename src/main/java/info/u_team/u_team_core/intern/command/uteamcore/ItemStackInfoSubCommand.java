package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.*;
import net.minecraft.item.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ItemStackInfoSubCommand {
	
	private static final String SUCCESS_TRANSLATION_STRING = "commands.uteamcore.stackinfo.success.";
	
	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("stackinfo").executes(context -> execute(context.getSource()));
	}
	
	private static int execute(CommandSource source) throws CommandSyntaxException {
		final ItemStack stack = source.asPlayer().getHeldItemMainhand();
		final Item item = stack.getItem();
		
		source.sendFeedback(new TranslationTextComponent(SUCCESS_TRANSLATION_STRING + "item", createRegistryInfo(item)), false);
		
		if (item instanceof BlockItem) {
			source.sendFeedback(new TranslationTextComponent(SUCCESS_TRANSLATION_STRING + "block", createRegistryInfo(((BlockItem) item).getBlock())), false);
		}
		
		if (stack.hasTag()) {
			source.sendFeedback(new TranslationTextComponent(SUCCESS_TRANSLATION_STRING + "nbt", stack.getTag().toFormattedComponent()), false);
		}
		return 0;
	}
	
	private static ITextComponent createRegistryInfo(IForgeRegistryEntry<?> entry) {
		final ITextComponent component = new StringTextComponent(entry.getRegistryName().toString());
		final Style style = component.getStyle();
		final String className = getClassString(entry);
		style.setColor(TextFormatting.AQUA);
		style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent(className).applyTextStyle(TextFormatting.YELLOW)));
		style.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, className));
		return component;
	}
	
	private static String getClassString(Object object) {
		return object.getClass().getName() + "@" + Integer.toHexString(object.hashCode());
	}
}

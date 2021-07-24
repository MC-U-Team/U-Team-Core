package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ItemStackInfoSubCommand {
	
	private static final String SUCCESS_TRANSLATION_STRING = "commands.uteamcore.stackinfo.success.";
	
	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("stackinfo").executes(context -> execute(context.getSource()));
	}
	
	private static int execute(CommandSource source) throws CommandSyntaxException {
		final ItemStack stack = source.getPlayerOrException().getMainHandItem();
		final Item item = stack.getItem();
		
		source.sendSuccess(new TranslationTextComponent(SUCCESS_TRANSLATION_STRING + "item", createRegistryInfo(item)), false);
		
		if (item instanceof BlockItem) {
			source.sendSuccess(new TranslationTextComponent(SUCCESS_TRANSLATION_STRING + "block", createRegistryInfo(((BlockItem) item).getBlock())), false);
		}
		
		if (stack.hasTag()) {
			source.sendSuccess(new TranslationTextComponent(SUCCESS_TRANSLATION_STRING + "nbt", stack.getTag().getPrettyDisplay()), false);
		}
		return 0;
	}
	
	private static ITextComponent createRegistryInfo(IForgeRegistryEntry<?> entry) {
		final IFormattableTextComponent component = new StringTextComponent(entry.getRegistryName().toString());
		Style style = component.getStyle();
		final String className = getClassString(entry);
		style = style.withColor(TextFormatting.AQUA);
		style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent(className).withStyle(TextFormatting.YELLOW)));
		style = style.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, className));
		component.setStyle(style);
		return component;
	}
	
	private static String getClassString(Object object) {
		return object.getClass().getName() + "@" + Integer.toHexString(object.hashCode());
	}
}

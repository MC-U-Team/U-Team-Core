package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ItemStackInfoSubCommand {
	
	private static final String SUCCESS_TRANSLATION_STRING = "commands.uteamcore.stackinfo.success.";
	
	public static ArgumentBuilder<CommandSourceStack, ?> register() {
		return Commands.literal("stackinfo").executes(context -> execute(context.getSource()));
	}
	
	private static int execute(CommandSourceStack source) throws CommandSyntaxException {
		final ItemStack stack = source.getPlayerOrException().getMainHandItem();
		final Item item = stack.getItem();
		
		source.sendSuccess(new TranslatableComponent(SUCCESS_TRANSLATION_STRING + "item", createRegistryInfo(item)), false);
		
		if (item instanceof BlockItem) {
			source.sendSuccess(new TranslatableComponent(SUCCESS_TRANSLATION_STRING + "block", createRegistryInfo(((BlockItem) item).getBlock())), false);
		}
		
		if (stack.hasTag()) {
			source.sendSuccess(new TranslatableComponent(SUCCESS_TRANSLATION_STRING + "nbt", stack.getTag().getPrettyDisplay()), false);
		}
		return 0;
	}
	
	private static Component createRegistryInfo(IForgeRegistryEntry<?> entry) {
		final MutableComponent component = new TextComponent(entry.getRegistryName().toString());
		Style style = component.getStyle();
		final String className = getClassString(entry);
		style = style.withColor(ChatFormatting.AQUA);
		style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent(className).withStyle(ChatFormatting.YELLOW)));
		style = style.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, className));
		component.setStyle(style);
		return component;
	}
	
	private static String getClassString(Object object) {
		return object.getClass().getName() + "@" + Integer.toHexString(object.hashCode());
	}
}

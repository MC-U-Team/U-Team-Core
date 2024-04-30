package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemStackInfoSubCommand {
	
	private static final String SUCCESS_TRANSLATION_STRING = "commands.uteamcore.stackinfo.success.";
	
	public static ArgumentBuilder<CommandSourceStack, ?> register() {
		return Commands.literal("stackinfo").executes(context -> execute(context.getSource()));
	}
	
	private static int execute(CommandSourceStack source) throws CommandSyntaxException {
		final ItemStack stack = source.getPlayerOrException().getMainHandItem();
		final Item item = stack.getItem();
		
		source.sendSuccess(() -> Component.translatable(SUCCESS_TRANSLATION_STRING + "item", createRegistryInfo(item, Registries.ITEM)), false);
		
		if (item instanceof final BlockItem blockItem) {
			source.sendSuccess(() -> Component.translatable(SUCCESS_TRANSLATION_STRING + "block", createRegistryInfo(blockItem.getBlock(), Registries.BLOCK)), false);
		}
		
		// TODO evaluate how and what to show with components
		// if (stack.hasTag()) {
		// final MutableComponent component = NbtUtils.toPrettyComponent(stack.getTag()).copy();
		// final Style style = component.getStyle() //
		// .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable(SUCCESS_TRANSLATION_STRING +
		// "copy").withStyle(ChatFormatting.GREEN))) //
		// .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, component.getString()));
		// component.setStyle(style);
		// source.sendSuccess(() -> Component.translatable(SUCCESS_TRANSLATION_STRING + "nbt", component), false);
		// }
		return 0;
	}
	
	private static <T> Component createRegistryInfo(T entry, ResourceKey<Registry<T>> key) {
		final Registry<T> vanillaRegistry = RegistryUtil.getBuiltInRegistry(key);
		final MutableComponent component = Component.literal(vanillaRegistry.getKey(entry).toString());
		final String className = getClassString(entry);
		component.withStyle(style -> style.withColor(ChatFormatting.AQUA) //
				.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(className).withStyle(ChatFormatting.YELLOW))) //
				.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, className)));
		return component;
	}
	
	private static String getClassString(Object object) {
		return object.getClass().getName() + "@" + Integer.toHexString(object.hashCode());
	}
}

package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

public class SubCommandUTeamCoreDumbItemStack {
	
	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("dumbstack").executes(ctx -> execute(ctx.getSource()));
	}
	
	private static int execute(CommandSource source) throws CommandException {
		EntityPlayerMP player = null;
		try {
			player = source.asPlayer();
		} catch (CommandSyntaxException ex) {
			throw new CommandException(new TextComponentString(ex.getMessage()));
		}
		ItemStack stack = player.getHeldItemMainhand();
		source.sendFeedback(new TextComponentString("Item: " + stack.getItem().getRegistryName()), true);
		source.sendFeedback(new TextComponentString("Nbt: "), true);
		source.sendFeedback(stack.getTag() == null ? new TextComponentString("None") : stack.getTag().toFormattedComponent(), true);
		return 0;
	}
}

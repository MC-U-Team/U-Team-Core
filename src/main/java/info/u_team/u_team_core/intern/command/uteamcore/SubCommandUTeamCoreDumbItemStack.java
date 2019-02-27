package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
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
		Item item = stack.getItem();
		NBTTagCompound compound = stack.getTag();
		source.sendFeedback(new TextComponentString("Item: " + item.getRegistryName() + " (" + item + ")"), true);
		if (compound != null) {
			source.sendFeedback(new TextComponentString("NBT: ").appendSibling(compound.toFormattedComponent()), true);
		}
		return 0;
	}
}

package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;

public class SubCommandUTeamCoreDumbItemStack {
	
	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("dumbstack").executes(ctx -> execute(ctx.getSource()));
	}
	
	private static int execute(CommandSource source) throws CommandException {
		
		ServerPlayerEntity player = null;
		try {
			player = source.asPlayer();
		} catch (CommandSyntaxException ex) {
			throw new CommandException(new StringTextComponent(ex.getMessage()));
		}
		ItemStack stack = player.getHeldItemMainhand();
		Item item = stack.getItem();
		CompoundNBT compound = stack.getTag();
		source.sendFeedback(new StringTextComponent("Item: " + item.getRegistryName() + " (" + item + ")"), true);
		if (compound != null) {
			source.sendFeedback(new StringTextComponent("NBT: ").appendSibling(compound.toFormattedComponent()), true);
		}
		return 0;
	}
}

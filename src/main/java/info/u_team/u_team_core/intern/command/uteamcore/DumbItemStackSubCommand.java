package info.u_team.u_team_core.intern.command.uteamcore;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.block.Block;
import net.minecraft.command.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;

public class DumbItemStackSubCommand {
	
	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("dumbstack").executes(context -> execute(context.getSource()));
	}
	
	private static int execute(CommandSource source) throws CommandSyntaxException {
		final ServerPlayerEntity player = source.asPlayer();
		final ItemStack stack = player.getHeldItemMainhand();
		final Item item = stack.getItem();
		final CompoundNBT compound = stack.getTag();
		
		source.sendFeedback(new StringTextComponent("Item: " + item.getRegistryName() + " (" + getClassString(item) + ")"), false);
		if (item instanceof BlockItem) {
			final Block block = ((BlockItem) item).getBlock();
			source.sendFeedback(new StringTextComponent("Block: " + block.getRegistryName() + " (" + getClassString(block) + ")"), false);
		}
		
		if (compound != null) {
			source.sendFeedback(new StringTextComponent("NBT: ").appendSibling(compound.toFormattedComponent()), false);
		}
		return 0;
	}
	
	private static String getClassString(Object object) {
		return object.getClass().getName() + "@" + Integer.toHexString(object.hashCode());
	}
}

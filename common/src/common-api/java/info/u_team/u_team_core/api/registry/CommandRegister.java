package info.u_team.u_team_core.api.registry;

import java.util.function.Consumer;

import com.mojang.brigadier.CommandDispatcher;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public interface CommandRegister {
	
	CommandRegister INSTANCE = ServiceUtil.loadOne(CommandRegister.class);
	
	static void register(Consumer<CommandHandler> consumer) {
		CommandRegister.INSTANCE.registerCommand(consumer);
	}
	
	void registerCommand(Consumer<CommandHandler> consumer);
	
	static record CommandHandler(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
	}
}

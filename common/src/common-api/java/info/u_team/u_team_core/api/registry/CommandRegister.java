package info.u_team.u_team_core.api.registry;

import java.util.function.Consumer;

import com.mojang.brigadier.CommandDispatcher;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public interface CommandRegister {
	
	static CommandRegister create() {
		return Factory.INSTANCE.create();
	}
	
	void register(Consumer<CommandHandler> consumer);
	
	void register();
	
	static record CommandHandler(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
	}
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
		
		CommandRegister create();
	}
}

package info.u_team.u_team_test.test_multiloader.fabric.init;

import com.mojang.brigadier.CommandDispatcher;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderMod;
import info.u_team.u_team_test.test_multiloader.command.TestNetworkCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

@Construct(modid = TestMultiLoaderMod.MODID)
public class TestMultiLoaderCommands implements ModConstruct {
	
	private static void onRegisterCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
		TestNetworkCommand.register(dispatcher);
	}
	
	@Override
	public void construct() {
		CommandRegistrationCallback.EVENT.register(TestMultiLoaderCommands::onRegisterCommands);
	}
	
}

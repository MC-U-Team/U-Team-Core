package info.u_team.u_team_test.test_multiloader.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderNetwork;
import info.u_team.u_team_test.test_multiloader.messages.TestServerToClientMessage;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class TestNetworkCommand {
	
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("test_multiloader_network") //
				.then(Commands.literal("explicit_s2c").executes(context -> executeExplicitS2C(context.getSource()))));
	}
	
	private static int executeExplicitS2C(CommandSourceStack source) throws CommandSyntaxException {
		final ServerPlayer player = source.getPlayerOrException();
		TestMultiLoaderNetwork.NETWORK.sendToPlayer(player, new TestServerToClientMessage("Hello world from the server"));
		return 0;
	}
	
}

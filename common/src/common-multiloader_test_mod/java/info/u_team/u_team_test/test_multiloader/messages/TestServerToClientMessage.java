package info.u_team.u_team_test.test_multiloader.messages;

import info.u_team.u_team_test.test_multiloader.messages.TestMessageHandler.CommonTestMessage;
import net.minecraft.network.FriendlyByteBuf;

public record TestServerToClientMessage(String string) implements CommonTestMessage {
	
	public static void encode(TestServerToClientMessage message, FriendlyByteBuf byteBuf) {
		byteBuf.writeUtf(message.string);
	}
	
	public static TestServerToClientMessage decode(FriendlyByteBuf byteBuf) {
		return new TestServerToClientMessage(byteBuf.readUtf());
	}
}

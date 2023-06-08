package info.u_team.u_team_test.test_multiloader.messages;

import net.minecraft.network.FriendlyByteBuf;

public record TestServerToClientMessage(String string) implements TestMessage {
	
	public static void encode(TestServerToClientMessage message, FriendlyByteBuf byteBuf) {
		byteBuf.writeUtf(message.string);
	}
	
	public static TestServerToClientMessage decode(FriendlyByteBuf byteBuf) {
		return new TestServerToClientMessage(byteBuf.readUtf());
	}
}

package info.u_team.u_team_test.test_multiloader.messages;

import net.minecraft.network.FriendlyByteBuf;

public record TestClientToServerMessage(String string) implements TestMessage {
	
	public static void encode(TestClientToServerMessage message, FriendlyByteBuf byteBuf) {
		byteBuf.writeUtf(message.string);
	}
	
	public static TestClientToServerMessage decode(FriendlyByteBuf byteBuf) {
		return new TestClientToServerMessage(byteBuf.readUtf());
	}
}

package info.u_team.u_team_core.api.sync;

import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

public class MessageHolder extends DataHolder {
	
	private Supplier<FriendlyByteBuf> send;
	private final Consumer<FriendlyByteBuf> receive;
	
	private boolean isTriggered;
	
	public MessageHolder(Consumer<FriendlyByteBuf> receive) {
		this.receive = receive;
	}
	
	@Override
	public FriendlyByteBuf get() {
		return send.get();
	}
	
	@Override
	public void set(FriendlyByteBuf buffer) {
		receive.accept(buffer);
	}
	
	@Override
	public boolean checkAndClearUpdateFlag() {
		if (isTriggered) {
			isTriggered = false;
			return true;
		}
		return false;
	}
	
	public void triggerMessage(Supplier<FriendlyByteBuf> send) {
		isTriggered = true;
		this.send = send;
	}
	
	public static class EmptyMessageHolder extends MessageHolder {
		
		public EmptyMessageHolder(Runnable receive) {
			super(packet -> receive.run());
		}
		
		public void triggerMessage() {
			triggerMessage(() -> new FriendlyByteBuf(Unpooled.EMPTY_BUFFER));
		}
		
	}
}

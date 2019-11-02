package info.u_team.u_team_core.api.sync;

import java.util.function.*;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;

public class MessageHolder extends BufferReferenceHolder {
	
	private Supplier<PacketBuffer> send;
	private final Consumer<PacketBuffer> receive;
	
	private boolean isTriggered;
	
	public MessageHolder(Consumer<PacketBuffer> receive) {
		this.receive = receive;
	}
	
	@Override
	public PacketBuffer get() {
		return send.get();
	}
	
	@Override
	public void set(PacketBuffer buffer) {
		receive.accept(buffer);
	}
	
	@Override
	public boolean isDirty() {
		if (isTriggered) {
			isTriggered = false;
			return true;
		}
		return false;
	}
	
	public void triggerMessage(Supplier<PacketBuffer> send) {
		isTriggered = true;
		this.send = send;
	}
	
	public static class EmptyMessageHolder extends MessageHolder {
		
		public EmptyMessageHolder(Runnable receive) {
			super(packet -> receive.run());
		}
		
		public void triggerMessage() {
			triggerMessage(() -> new PacketBuffer(Unpooled.EMPTY_BUFFER));
		}
		
	}
}

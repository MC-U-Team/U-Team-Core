package info.u_team.u_team_core.api.sync;

import java.util.function.*;

import net.minecraft.network.PacketBuffer;

public abstract class BufferReferenceHolder {
	
	private PacketBuffer lastKnownValue;
	
	public abstract PacketBuffer get();
	
	public abstract void set(PacketBuffer buffer);
	
	public boolean isDirty() {
		final PacketBuffer buffer = get();
		final boolean dirty = !buffer.equals(lastKnownValue);
		lastKnownValue = buffer;
		return dirty;
	}
	
	public static final BufferReferenceHolder create(Supplier<PacketBuffer> get, Consumer<PacketBuffer> set) {
		return new BufferReferenceHolder() {
			
			@Override
			public PacketBuffer get() {
				return get.get();
			}
			
			@Override
			public void set(PacketBuffer buffer) {
				set.accept(buffer);
			}
		};
	}
	
}

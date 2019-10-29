package info.u_team.u_team_core.api.sync;

import java.util.function.*;

import io.netty.buffer.Unpooled;
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
	
	public static final BufferReferenceHolder createHolder(Supplier<PacketBuffer> get, Consumer<PacketBuffer> set) {
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
	
	public static final BufferReferenceHolder createIntHolder(IntSupplier get, IntConsumer set) {
		return new BufferReferenceHolder() {
			
			@Override
			public PacketBuffer get() {
				return new PacketBuffer(Unpooled.copyInt(get.getAsInt()));
			}
			
			@Override
			public void set(PacketBuffer buffer) {
				set.accept(buffer.readInt());
			}
		};
	}
	
	public static final BufferReferenceHolder createShortHolder(Supplier<Short> get, Consumer<Short> set) {
		return new BufferReferenceHolder() {
			
			@Override
			public PacketBuffer get() {
				return new PacketBuffer(Unpooled.copyShort(get.get()));
			}
			
			@Override
			public void set(PacketBuffer buffer) {
				set.accept(buffer.readShort());
			}
		};
	}
	
	public static final BufferReferenceHolder createLongHolder(LongSupplier get, LongConsumer set) {
		return new BufferReferenceHolder() {
			
			@Override
			public PacketBuffer get() {
				return new PacketBuffer(Unpooled.copyLong(get.getAsLong()));
			}
			
			@Override
			public void set(PacketBuffer buffer) {
				set.accept(buffer.readLong());
			}
		};
	}
	
}

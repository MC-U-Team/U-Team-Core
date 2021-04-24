package info.u_team.u_team_core.api.sync;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
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
	
	public static final BufferReferenceHolder createByteHolder(Supplier<Byte> get, Consumer<Byte> set) {
		return new BufferReferenceHolder() {
			
			@Override
			public PacketBuffer get() {
				return new PacketBuffer(Unpooled.buffer(1).writeByte(get.get()));
			}
			
			@Override
			public void set(PacketBuffer buffer) {
				set.accept(buffer.readByte());
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
	
	public static final BufferReferenceHolder createFloatHolder(Supplier<Float> get, Consumer<Float> set) {
		return new BufferReferenceHolder() {
			
			@Override
			public PacketBuffer get() {
				return new PacketBuffer(Unpooled.copyFloat(get.get()));
			}
			
			@Override
			public void set(PacketBuffer buffer) {
				set.accept(buffer.readFloat());
			}
		};
	}
	
	public static final BufferReferenceHolder createDoubleHolder(Supplier<Double> get, Consumer<Double> set) {
		return new BufferReferenceHolder() {
			
			@Override
			public PacketBuffer get() {
				return new PacketBuffer(Unpooled.copyDouble(get.get()));
			}
			
			@Override
			public void set(PacketBuffer buffer) {
				set.accept(buffer.readDouble());
			}
		};
	}
	
	public static final BufferReferenceHolder createBooleanHolder(BooleanSupplier get, BooleanConsumer set) {
		return new BufferReferenceHolder() {
			
			@Override
			public PacketBuffer get() {
				return new PacketBuffer(Unpooled.copyBoolean(get.getAsBoolean()));
			}
			
			@Override
			public void set(PacketBuffer buffer) {
				set.accept(buffer.readBoolean());
			}
		};
	}
}

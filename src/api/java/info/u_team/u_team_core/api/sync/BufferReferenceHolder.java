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
import net.minecraft.network.FriendlyByteBuf;

public abstract class BufferReferenceHolder {
	
	private FriendlyByteBuf lastKnownValue;
	
	public abstract FriendlyByteBuf get();
	
	public abstract void set(FriendlyByteBuf buffer);
	
	public boolean isDirty() {
		final FriendlyByteBuf buffer = get();
		final boolean dirty = !buffer.equals(lastKnownValue);
		lastKnownValue = buffer;
		return dirty;
	}
	
	public static final BufferReferenceHolder createHolder(Supplier<FriendlyByteBuf> get, Consumer<FriendlyByteBuf> set) {
		return new BufferReferenceHolder() {
			
			@Override
			public FriendlyByteBuf get() {
				return get.get();
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				set.accept(buffer);
			}
		};
	}
	
	public static final BufferReferenceHolder createIntHolder(IntSupplier get, IntConsumer set) {
		return new BufferReferenceHolder() {
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(Unpooled.copyInt(get.getAsInt()));
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				set.accept(buffer.readInt());
			}
		};
	}
	
	public static final BufferReferenceHolder createByteHolder(Supplier<Byte> get, Consumer<Byte> set) {
		return new BufferReferenceHolder() {
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(Unpooled.buffer(1).writeByte(get.get()));
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				set.accept(buffer.readByte());
			}
		};
	}
	
	public static final BufferReferenceHolder createShortHolder(Supplier<Short> get, Consumer<Short> set) {
		return new BufferReferenceHolder() {
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(Unpooled.copyShort(get.get()));
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				set.accept(buffer.readShort());
			}
		};
	}
	
	public static final BufferReferenceHolder createLongHolder(LongSupplier get, LongConsumer set) {
		return new BufferReferenceHolder() {
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(Unpooled.copyLong(get.getAsLong()));
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				set.accept(buffer.readLong());
			}
		};
	}
	
	public static final BufferReferenceHolder createFloatHolder(Supplier<Float> get, Consumer<Float> set) {
		return new BufferReferenceHolder() {
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(Unpooled.copyFloat(get.get()));
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				set.accept(buffer.readFloat());
			}
		};
	}
	
	public static final BufferReferenceHolder createDoubleHolder(Supplier<Double> get, Consumer<Double> set) {
		return new BufferReferenceHolder() {
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(Unpooled.copyDouble(get.get()));
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				set.accept(buffer.readDouble());
			}
		};
	}
	
	public static final BufferReferenceHolder createBooleanHolder(BooleanSupplier get, BooleanConsumer set) {
		return new BufferReferenceHolder() {
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(Unpooled.copyBoolean(get.getAsBoolean()));
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				set.accept(buffer.readBoolean());
			}
		};
	}
}

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

/**
 * This class holds a data buffer with setter and getter for synchronizing. Furthermore the data holder implemented a
 * method to check if the data has changed and should be synchronized.
 * 
 * @author HyCraftHD
 */
public abstract class DataHolder {
	
	private FriendlyByteBuf lastKnownValue;
	
	/**
	 * Should return an instance of {@link FriendlyByteBuf} that cannot be manipulated after that call. Generally it is a
	 * good idea to return a copy of the original data buffer here.
	 * 
	 * @return A buffer for synchronizing
	 */
	public abstract FriendlyByteBuf get();
	
	/**
	 * Sets the received instance of the {@link FriendlyByteBuf}. From this buffer the data can be read.
	 * 
	 * @param byteBuf The received buffer
	 */
	public abstract void set(FriendlyByteBuf byteBuf);
	
	/**
	 * Checks if the data has changed. Resets the changed bit afterwards. For performance reasons this method should be
	 * overwritten if the change check can be implemented directly on the data types. The default methods compared the
	 * buffers.
	 * 
	 * @return True if data has changed
	 */
	public boolean checkAndClearUpdateFlag() {
		final var buffer = get();
		final var changed = !buffer.equals(lastKnownValue);
		lastKnownValue = buffer;
		return changed;
	}
	
	public static final DataHolder createHolder(Supplier<FriendlyByteBuf> supplier, Consumer<FriendlyByteBuf> consumer) {
		return new DataHolder() {
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(supplier.get().copy());
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				consumer.accept(buffer);
			}
		};
	}
	
	public static final DataHolder createByteHolder(Supplier<Byte> supplier, Consumer<Byte> consumer) {
		return new DataHolder() {
			
			private byte lastKnownValue;
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(Unpooled.buffer(1).writeByte(supplier.get()));
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				consumer.accept(buffer.readByte());
			}
			
			@Override
			public boolean checkAndClearUpdateFlag() {
				final byte value = supplier.get();
				final var changed = value != lastKnownValue;
				lastKnownValue = value;
				return changed;
			}
		};
	}
	
	public static final DataHolder createShortHolder(Supplier<Short> supplier, Consumer<Short> consumer) {
		return new DataHolder() {
			
			private short lastKnownValue;
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(Unpooled.copyShort(supplier.get()));
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				consumer.accept(buffer.readShort());
			}
			
			@Override
			public boolean checkAndClearUpdateFlag() {
				final short value = supplier.get();
				final var changed = value != lastKnownValue;
				lastKnownValue = value;
				return changed;
			}
		};
	}
	
	public static final DataHolder createIntHolder(IntSupplier supplier, IntConsumer consumer) {
		return new DataHolder() {
			
			private int lastKnownValue;
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(Unpooled.copyInt(supplier.getAsInt()));
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				consumer.accept(buffer.readInt());
			}
			
			@Override
			public boolean checkAndClearUpdateFlag() {
				final int value = supplier.getAsInt();
				final var changed = value != lastKnownValue;
				lastKnownValue = value;
				return changed;
			}
		};
	}
	
	public static final DataHolder createLongHolder(LongSupplier supplier, LongConsumer consumer) {
		return new DataHolder() {
			
			private long lastKnownValue;
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(Unpooled.copyLong(supplier.getAsLong()));
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				consumer.accept(buffer.readLong());
			}
			
			@Override
			public boolean checkAndClearUpdateFlag() {
				final long value = supplier.getAsLong();
				final var changed = value != lastKnownValue;
				lastKnownValue = value;
				return changed;
			}
		};
	}
	
	public static final DataHolder createFloatHolder(Supplier<Float> supplier, Consumer<Float> consumer) {
		return new DataHolder() {
			
			private float lastKnownValue;
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(Unpooled.copyFloat(supplier.get()));
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				consumer.accept(buffer.readFloat());
			}
			
			@Override
			public boolean checkAndClearUpdateFlag() {
				final float value = supplier.get();
				final var changed = value != lastKnownValue;
				lastKnownValue = value;
				return changed;
			}
		};
	}
	
	public static final DataHolder createDoubleHolder(Supplier<Double> supplier, Consumer<Double> consumer) {
		return new DataHolder() {
			
			private double lastKnownValue;
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(Unpooled.copyDouble(supplier.get()));
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				consumer.accept(buffer.readDouble());
			}
			
			@Override
			public boolean checkAndClearUpdateFlag() {
				final double value = supplier.get();
				final var changed = value != lastKnownValue;
				lastKnownValue = value;
				return changed;
			}
		};
	}
	
	public static final DataHolder createBooleanHolder(BooleanSupplier supplier, BooleanConsumer consumer) {
		return new DataHolder() {
			
			private boolean lastKnownValue;
			
			@Override
			public FriendlyByteBuf get() {
				return new FriendlyByteBuf(Unpooled.copyBoolean(supplier.getAsBoolean()));
			}
			
			@Override
			public void set(FriendlyByteBuf buffer) {
				consumer.accept(buffer.readBoolean());
			}
			
			@Override
			public boolean checkAndClearUpdateFlag() {
				final boolean value = supplier.getAsBoolean();
				final var changed = value != lastKnownValue;
				lastKnownValue = value;
				return changed;
			}
		};
	}
}

package info.u_team.u_team_core.api.sync;

import java.nio.ByteBuffer;
import java.util.function.*;

public abstract class BufferReferenceHolder {
	
	private ByteBuffer lastKnownValue;
	
	public abstract ByteBuffer get();
	
	public abstract void set(ByteBuffer buffer);
	
	public boolean isDirty() {
		final ByteBuffer buffer = get();
		final boolean flag = buffer != lastKnownValue;
		lastKnownValue = buffer;
		return flag;
	}
	
	public static final BufferReferenceHolder create(Supplier<ByteBuffer> get, Consumer<ByteBuffer> set) {
		return new BufferReferenceHolder() {
			
			@Override
			public ByteBuffer get() {
				return get.get();
			}
			
			@Override
			public void set(ByteBuffer buffer) {
				set.accept(buffer);
			}
		};
	}
	
}

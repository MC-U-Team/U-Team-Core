package info.u_team.u_team_core.util;

import java.util.function.Consumer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

public class ByteBufUtil {
	
	public static byte[] transferToByteArray(ByteBuf buffer) {
		final byte[] data = new byte[buffer.readableBytes()];
		buffer.readBytes(data);
		return data;
	}
	
	public static byte[] releaseToByteArray(ByteBuf buffer) {
		try {
			buffer.readerIndex(0);
			return ByteBufUtil.transferToByteArray(buffer);
		} finally {
			buffer.release();
		}
	}
	
	public static byte[] writeCustomDataToByteArray(Consumer<FriendlyByteBuf> writer) {
		final FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
		writer.accept(buffer);
		return releaseToByteArray(buffer);
	}
	
}

package info.u_team.u_team_core.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;

public class NBTStreamUtil {

	public static CompoundTag readNBTFromFile(File file) throws IOException {
		return readNBTFromStream(new FileInputStream(file));
	}

	public static CompoundTag readNBTFromStream(InputStream stream) throws IOException {
		CompoundTag tag;
		try {
			tag = NbtIo.readCompressed(stream);
		} finally {
			stream.close();
		}
		return tag;
	}

	public static void writeNBTToFile(CompoundTag tag, File file) throws IOException {
		writeNBTToStream(tag, new FileOutputStream(file));
	}

	public static void writeNBTToStream(CompoundTag tag, OutputStream stream) throws IOException {
		try {
			NbtIo.writeCompressed(tag, stream);
		} finally {
			stream.flush();
			stream.close();
		}
	}

}

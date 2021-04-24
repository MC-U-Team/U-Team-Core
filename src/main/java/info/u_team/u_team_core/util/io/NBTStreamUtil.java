package info.u_team.u_team_core.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;

public class NBTStreamUtil {
	
	public static CompoundNBT readNBTFromFile(File file) throws IOException {
		return readNBTFromStream(new FileInputStream(file));
	}
	
	public static CompoundNBT readNBTFromStream(InputStream stream) throws IOException {
		CompoundNBT tag;
		try {
			tag = CompressedStreamTools.readCompressed(stream);
		} finally {
			stream.close();
		}
		return tag;
	}
	
	public static void writeNBTToFile(CompoundNBT tag, File file) throws IOException {
		writeNBTToStream(tag, new FileOutputStream(file));
	}
	
	public static void writeNBTToStream(CompoundNBT tag, OutputStream stream) throws IOException {
		try {
			CompressedStreamTools.writeCompressed(tag, stream);
		} finally {
			stream.flush();
			stream.close();
		}
	}
	
}

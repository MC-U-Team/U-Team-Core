package info.u_team.u_team_core.util.io;

import java.io.*;

import net.minecraft.nbt.*;

public class NBTStreamUtil {
	
	public static NBTTagCompound readNBTFromFile(File file) throws IOException {
		return readNBTFromStream(new FileInputStream(file));
	}
	
	public static NBTTagCompound readNBTFromStream(InputStream stream) throws IOException {
		NBTTagCompound tag;
		try {
			tag = CompressedStreamTools.readCompressed(stream);
		} finally {
			stream.close();
		}
		return tag;
	}
	
	public static void writeNBTToFile(NBTTagCompound tag, File file) throws IOException {
		writeNBTToStream(tag, new FileOutputStream(file));
	}
	
	public static void writeNBTToStream(NBTTagCompound tag, OutputStream stream) throws IOException {
		try {
			CompressedStreamTools.writeCompressed(tag, stream);
		} finally {
			stream.flush();
			stream.close();
		}
	}
	
}

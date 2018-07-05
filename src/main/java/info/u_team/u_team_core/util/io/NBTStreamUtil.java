/*******************************************************************************
 * Copyright 2018 U-Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package info.u_team.u_team_core.util.io;

import java.io.*;

import net.minecraft.nbt.*;

/**
 * NBT utility
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
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

/*-*****************************************************************************
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

import net.minecraft.util.ResourceLocation;

/**
 * File utility
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public class FileUtil {
	
	public static InputStream getInputStreamFromFile(File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}
	
	public static InputStream getInputStreamFromResource(String resource) {
		return FileUtil.class.getResourceAsStream(resource);
	}
	
	public static InputStream getInputStreamFromResource(ResourceLocation resource) {
		return getInputStreamFromResource("/assets/" + resource.getNamespace() + "/" + resource.getPath());
	}
	
	public static File[] getFilesInDirectionary(File file) throws Exception {
		return file.listFiles();
	}
	
	public static String[] getFileNamesInDirectionary(File file, String toreplace, String replaced) throws Exception {
		if (file.exists()) {
			File[] files = getFilesInDirectionary(file);
			if (files != null) {
				String[] names = new String[files.length];
				for (int i = 0; i < files.length; i++) {
					names[i] = files[i].getName().replaceAll(toreplace, replaced);
				}
				return names;
			}
		}
		return new String[] {};
	}
	
	public static File getMainDirectory() {
		return new File(System.getProperty("user.dir"));
	}
	
}

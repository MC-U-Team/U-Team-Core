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
		return getInputStreamFromResource("/assets/" + resource.getResourceDomain() + "/" + resource.getResourcePath());
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

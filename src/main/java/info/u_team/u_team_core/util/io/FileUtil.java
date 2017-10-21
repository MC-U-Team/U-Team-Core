package info.u_team.u_team_core.util.io;

import java.io.*;

import net.minecraft.util.ResourceLocation;

public class FileUtil {
	
	public static InputStream getInputStreamFromFile(File file) throws IOException {
		return new FileInputStream(file);
	}
	
	public static InputStream getInputStreamFromResource(String resource) throws IOException {
		return FileUtil.class.getResourceAsStream(resource);
	}
	
	public static InputStream getInputStreamFromResource(ResourceLocation resource) throws IOException {
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
	
	public File getMainDirectory() {
		return new File(System.getProperty("user.dir"));
	}
	
}

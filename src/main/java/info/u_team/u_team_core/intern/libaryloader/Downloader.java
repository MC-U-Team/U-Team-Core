package info.u_team.u_team_core.intern.libaryloader;

import java.io.File;
import java.net.URL;
import java.util.*;

import org.apache.commons.io.FileUtils;

import info.u_team.u_team_core.util.io.FileUtil;

public class Downloader {
	
	private File path;
	private ArrayList<File> files = new ArrayList<>();
	
	public Downloader() {
		createLibaryPath();
		download();
	}
	
	private void download() {
		Map<String, String> map = Config.getDownloadMap();
		
		map.forEach((link, name) -> {
			try {
				File file = new File(path, name);
				if (!file.exists()) {
					FileUtils.copyURLToFile(new URL(link), file);
					System.out.println("Downloaded " + name + " from " + link + " to " + file + ".");
				} else {
					System.out.println("Found " + name + " in path " + file + ". No need to download.");
				}
				files.add(file);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		System.out.println("All downloads completed.");
		
	}
	
	private void createLibaryPath() {
		path = new File(FileUtil.getMainDirectory(), "ucore-libaries");
		if (!path.exists()) {
			path.mkdirs();
		}
	}
	
	public ArrayList<File> getFiles() {
		return files;
	}
}

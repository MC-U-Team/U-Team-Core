package info.u_team.u_team_core.intern.libaryloader;

import java.util.Map;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.*;

/**
 * Libary loader as coremod to inject libaries into classloader
 * 
 * @author HyCraftHD
 * @date 25.10.2017
 *
 */
public class LibaryLoader implements IFMLLoadingPlugin, IFMLCallHook {
	
	@Override
	public Void call() throws Exception {
		LaunchClassLoader loader = (LaunchClassLoader) this.getClass().getClassLoader();
		new Downloader().getFiles().forEach(file -> {
			try {
				loader.addURL(file.toURI().toURL());
				System.out.println("Loaded libary " + file + " to classloader");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		System.out.println("All libaries are loaded.");
		return null;
	}
	
	@Override
	public String[] getASMTransformerClass() {
		return null;
	}
	
	@Override
	public String getModContainerClass() {
		return null;
	}
	
	@Override
	public String getSetupClass() {
		return this.getClass().getName();
	}
	
	@Override
	public void injectData(Map<String, Object> data) {
	}
	
	@Override
	public String getAccessTransformerClass() {
		return null;
	}
	
}

package info.u_team.u_team_core.sub;

import info.u_team.u_team_core.sub.metadata.MetadataFetcher;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;

/**
 * Submod should implement this class
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 *
 */
public abstract class USubMod {
	
	private String modid;
	private String name, version;
	
	public USubMod() {
		ModContainer container = Loader.instance().activeModContainer();
		modid = container.getModId();
		name = container.getName();
		version = container.getVersion();
	}
	
	public void preinit(FMLPreInitializationEvent event) {
		USub.setID(modid);
		new MetadataFetcher(modid).setName(name).setVersion(version).applyMetadata(event.getModMetadata());
	}
	
	public void init(FMLInitializationEvent event) {
		USub.setID(modid);
	}
	
	public void postinit(FMLPostInitializationEvent event) {
		USub.setID(modid);
	}
	
}

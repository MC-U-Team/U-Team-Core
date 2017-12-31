package info.u_team.u_team_core.sub;

import org.apache.logging.log4j.Logger;

import info.u_team.u_team_core.sub.metadata.MetadataFetcher;
import info.u_team.u_team_core.updatechecker.UpdateCheckerRegistry;
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
	private String updateurl;
	
	protected Logger logger;
	
	public USubMod(String modid, String name, String version) {
		this(modid, name, version, null);
	}
	
	public USubMod(String modid, String name, String version, String updateurl) {
		this.modid = modid;
		this.name = name;
		this.version = version;
		this.updateurl = updateurl;
	}
	
	public void preinit(FMLPreInitializationEvent event) {
		USub.setID(modid);
		logger = event.getModLog();
		new MetadataFetcher(modid).setName(name).setVersion(version).applyMetadata(event.getModMetadata());
		if (updateurl != null) {
			UpdateCheckerRegistry.addMod(modid, updateurl);
		}
	}
	
	public void init(FMLInitializationEvent event) {
		USub.setID(modid);
	}
	
	public void postinit(FMLPostInitializationEvent event) {
		USub.setID(modid);
	}
	
}

package info.u_team.u_team_core.sub;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.event.*;

public abstract class USubMod {
	
	private String modid;
	
	protected Logger logger;
	
	public USubMod(String modid) {
		this.modid = modid;
	}
	
	public void preinit(FMLPreInitializationEvent event) {
		USub.setID(modid);
		logger = event.getModLog();
	}
	
	public void init(FMLInitializationEvent event) {
		USub.setID(modid);
	}
	
	public void postinit(FMLPostInitializationEvent event) {
		USub.setID(modid);
	}
	
}

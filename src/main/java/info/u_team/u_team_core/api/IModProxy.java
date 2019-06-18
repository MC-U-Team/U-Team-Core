package info.u_team.u_team_core.api;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.*;

/**
 * Simple interface for proxy default methods. Use with {@link DistExecutor#runForDist(client, server)}.
 * 
 * @author HyCraftHD
 *
 */
public interface IModProxy {
	
	/**
	 * Should be called when the constructor of the mod is called.
	 */
	void construct();
	
	/**
	 * Should be called in the {@link FMLCommonSetupEvent}
	 */
	void setup();
	
	/**
	 * Should be called in the {@link FMLLoadCompleteEvent}
	 */
	void complete();
	
}

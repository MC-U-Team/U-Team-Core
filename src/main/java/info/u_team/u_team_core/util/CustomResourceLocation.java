package info.u_team.u_team_core.util;

import net.minecraft.util.ResourceLocation;

/**
 * Resource location that can take an other resource location put append to its
 * resource path
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */

public class CustomResourceLocation extends ResourceLocation {
	
	public CustomResourceLocation(String domain, String patch) {
		super(domain, patch);
	}
	
	public CustomResourceLocation(ResourceLocation location, String appendix) {
		super(location.getResourceDomain(), location.getResourcePath() + appendix);
	}
	
}

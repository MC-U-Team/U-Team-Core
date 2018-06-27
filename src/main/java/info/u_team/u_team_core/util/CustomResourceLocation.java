package info.u_team.u_team_core.util;

import net.minecraft.util.ResourceLocation;

public class CustomResourceLocation extends ResourceLocation {
	
	public CustomResourceLocation(String domain, String patch) {
		super(domain, patch);
	}
	
	public CustomResourceLocation(ResourceLocation location, String appendix) {
		super(location.getResourceDomain(), location.getResourcePath() + appendix);
	}
	
}

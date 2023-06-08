package info.u_team.u_team_core.util;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;

public class KeyMappingUtil {
	
	private static final Handler HANDLER = ServiceUtil.loadOne(Handler.class);
	
	public static boolean matches(KeyMapping keyMapping, InputConstants.Key key) {
		return HANDLER.matches(keyMapping, key);
	}
	
	public static interface Handler {
		
		boolean matches(KeyMapping keyMapping, InputConstants.Key key);
	}
	
}

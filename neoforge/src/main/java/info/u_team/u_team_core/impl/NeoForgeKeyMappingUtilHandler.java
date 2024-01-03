package info.u_team.u_team_core.impl;

import com.mojang.blaze3d.platform.InputConstants;

import info.u_team.u_team_core.util.KeyMappingUtil;
import net.minecraft.client.KeyMapping;

public class NeoForgeKeyMappingUtilHandler implements KeyMappingUtil.Handler {
	
	@Override
	public boolean matches(KeyMapping keyMapping, InputConstants.Key key) {
		return keyMapping.isActiveAndMatches(key);
	}
	
}

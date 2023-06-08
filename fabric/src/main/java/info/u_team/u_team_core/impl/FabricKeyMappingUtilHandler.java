package info.u_team.u_team_core.impl;

import com.mojang.blaze3d.platform.InputConstants;

import info.u_team.u_team_core.util.KeyMappingUtil;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class FabricKeyMappingUtilHandler implements KeyMappingUtil.Handler {
	
	@Override
	public boolean matches(KeyMapping keyMapping, InputConstants.Key key) {
		return key != InputConstants.UNKNOWN && key.equals(KeyBindingHelper.getBoundKeyOf(keyMapping));
	}
	
}

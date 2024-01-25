package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.CreativeModeTabRegister;
import net.minecraft.world.item.CreativeModeTab;

public class NeoForgeCreativeModeTabRegisterBuilder implements CreativeModeTabRegister.Builder {
	
	@Override
	public CreativeModeTab.Builder create() {
		return CreativeModeTab.builder();
	}
	
}

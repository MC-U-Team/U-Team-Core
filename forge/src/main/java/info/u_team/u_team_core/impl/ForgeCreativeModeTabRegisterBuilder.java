package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.CreativeModeTabRegister;
import net.minecraft.world.item.CreativeModeTab;

public class ForgeCreativeModeTabRegisterBuilder implements CreativeModeTabRegister.Builder {
	
	@Override
	public CreativeModeTab.Builder create() {
		// .withTabsBefore(BUILDING_BLOCKS, COLORED_BLOCKS, NATURAL_BLOCKS, FUNCTIONAL_BLOCKS, REDSTONE_BLOCKS, HOTBAR, SEARCH,
		// TOOLS_AND_UTILITIES, COMBAT, FOOD_AND_DRINKS, INGREDIENTS, SPAWN_EGGS, OP_BLOCKS, INVENTORY); // TODO
		return CreativeModeTab.builder();
	}
	
}

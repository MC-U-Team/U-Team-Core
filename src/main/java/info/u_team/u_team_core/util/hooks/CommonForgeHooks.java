package info.u_team.u_team_core.util.hooks;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.*;

/**
 * Forge client hooks
 * 
 * @author HyCraftHD
 * @date 23.10.2017
 *
 */
public class CommonForgeHooks extends ForgeHooks {
	
	public static void addGrassSeed(ItemStack seed, int weight) {
		MinecraftForge.addGrassSeed(seed, weight);
	}
}

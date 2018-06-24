package info.u_team.u_team_core.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Common registry
 * 
 * @author HyCraftHD
 * @date 23.10.2017
 *
 */
public class CommonRegistry {
	
	public static void registerEventHandler(Object object) {
		MinecraftForge.EVENT_BUS.register(object);
	}
	
	public static void registerGuiHandler(Object mod, IGuiHandler handler) {
		NetworkRegistry.INSTANCE.registerGuiHandler(mod, handler);
	}
	
	public static void registerSmelting(ItemStack input, ItemStack output, float xp) {
		GameRegistry.addSmelting(input, output, xp);
	}
}

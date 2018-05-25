package info.u_team.u_team_core.util.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.*;
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
	
	public static void registerAchievementPage(AchievementPage page) {
		AchievementPage.registerAchievementPage(page);
	}
	
	public static void registerEventHandler(Object object) {
		MinecraftForge.EVENT_BUS.register(object);
		FMLCommonHandler.instance().bus().register(object);
	}
	
	public static void registerFuelHandler(IFuelHandler fueldhandler) {
		GameRegistry.registerFuelHandler(fueldhandler);
	}
	
	public static void registerGuiHandler(Object mod, IGuiHandler handler) {
		NetworkRegistry.INSTANCE.registerGuiHandler(mod, handler);
	}
	
	public static void registerRecipeShaped(ItemStack output, Object... obj) {
		GameRegistry.addShapedRecipe(output, obj);
	}
	
	public static void registerRecipeShapeless(ItemStack output, Object... obj) {
		GameRegistry.addShapelessRecipe(output, obj);
	}
	
	public static void registerSmelting(ItemStack input, ItemStack output, float xp) {
		GameRegistry.addSmelting(input, output, xp);
	}
}

package info.u_team.u_team_core.util.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.IFuelHandler;
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
	
	public void registerAchievementPage(AchievementPage page) {
		AchievementPage.registerAchievementPage(page);
	}
	
	public void registerEventHandler(Object object) {
		MinecraftForge.EVENT_BUS.register(object);
	}
	
	public void registerFuelHandler(IFuelHandler fueldhandler) {
		GameRegistry.registerFuelHandler(fueldhandler);
	}
	
	public void registerGuiHandler(Object mod, IGuiHandler handler) {
		NetworkRegistry.INSTANCE.registerGuiHandler(mod, handler);
	}
	
	public void registerRecipeShaped(ItemStack output, Object... obj) {
		GameRegistry.addShapedRecipe(output, obj);
	}
	
	public void registerRecipeShapeless(ItemStack output, Object... obj) {
		GameRegistry.addShapelessRecipe(output, obj);
	}
	
	public void registerSmelting(ItemStack input, ItemStack output, float xp) {
		GameRegistry.addSmelting(input, output, xp);
	}
}

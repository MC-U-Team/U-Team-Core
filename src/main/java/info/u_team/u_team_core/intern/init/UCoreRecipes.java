package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.intern.recipe.DyeableItemDyeRecipe;
import info.u_team.u_team_core.recipe.USpecialRecipeSerializer;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = UCoreMain.modid, bus = Bus.MOD)
public class UCoreRecipes {
	
	public static final IRecipeSerializer<DyeableItemDyeRecipe> CRAFTING_SPECIAL_ITEMDYE = new USpecialRecipeSerializer<>("crafting_special_itemdye", DyeableItemDyeRecipe::new);
	
	@SubscribeEvent
	public static void register(Register<IRecipeSerializer<?>> event) {
		BaseRegistryUtil.getAllGenericRegistryEntriesAndApplyNames(UCoreMain.modid, IRecipeSerializer.class).forEach(event.getRegistry()::register);
	}
	
}

package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.ingredient.ItemIngredient;
import info.u_team.u_team_core.intern.recipe.*;
import info.u_team.u_team_core.recipeserializer.USpecialRecipeSerializer;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = UCoreMain.MODID, bus = Bus.MOD)
public class UCoreRecipeSerializers {
	
	public static final USpecialRecipeSerializer<DyeableItemDyeRecipe> CRAFTING_SPECIAL_ITEMDYE = new USpecialRecipeSerializer<>("crafting_special_itemdye", DyeableItemDyeRecipe::new);
	
	public static final NoMirrorShapedRecipe.Serializer NO_MIRROR_SHAPED = new NoMirrorShapedRecipe.Serializer("crafting_shaped_no_mirror");
	
	@SubscribeEvent
	public static void register(Register<IRecipeSerializer<?>> event) {
		BaseRegistryUtil.getAllGenericRegistryEntriesAndApplyNames(UCoreMain.MODID, IRecipeSerializer.class).forEach(event.getRegistry()::register);
		CraftingHelper.register(new ResourceLocation(UCoreMain.MODID, "item"), ItemIngredient.Serializer.INSTANCE);
	}
	
}

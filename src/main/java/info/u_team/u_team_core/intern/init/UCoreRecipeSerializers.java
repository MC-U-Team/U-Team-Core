package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.ingredient.ItemIngredient;
import info.u_team.u_team_core.intern.recipe.*;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = UCoreMain.MODID, bus = Bus.MOD)
public class UCoreRecipeSerializers {
	
	public static final CommonDeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = CommonDeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UCoreMain.MODID);
	
	public static final RegistryObject<SpecialRecipeSerializer<DyeableItemDyeRecipe>> CRAFTING_SPECIAL_ITEMDYE = RECIPE_SERIALIZERS.register("crafting_special_itemdye", () -> new SpecialRecipeSerializer<>(DyeableItemDyeRecipe::new));
	
	public static final RegistryObject<NoMirrorShapedRecipe.Serializer> NO_MIRROR_SHAPED = RECIPE_SERIALIZERS.register("crafting_shaped_no_mirror", NoMirrorShapedRecipe.Serializer::new);
	
	public static void register(IEventBus bus) {
		RECIPE_SERIALIZERS.register(bus);
	}
	
	@SubscribeEvent
	public static void register(Register<IRecipeSerializer<?>> event) {
		CraftingHelper.register(new ResourceLocation(UCoreMain.MODID, "item"), ItemIngredient.Serializer.INSTANCE);
	}
	
}

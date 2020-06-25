package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.intern.recipe.*;
import net.minecraft.item.crafting.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.*;

@EventBusSubscriber(modid = UCoreMain.MODID, bus = Bus.MOD)
public class UCoreRecipeSerializers {
	
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UCoreMain.MODID);
	
	public static final RegistryObject<SpecialRecipeSerializer<DyeableItemDyeRecipe>> CRAFTING_SPECIAL_ITEMDYE = RECIPE_SERIALIZERS.register("crafting_special_itemdye", () -> new SpecialRecipeSerializer<>(DyeableItemDyeRecipe::new));
	
	public static final RegistryObject<NoMirrorShapedRecipe.Serializer> NO_MIRROR_SHAPED = RECIPE_SERIALIZERS.register("crafting_shaped_no_mirror", () -> new NoMirrorShapedRecipe.Serializer("crafting_shaped_no_mirror"));
	
	public static void register(IEventBus bus) {
		RECIPE_SERIALIZERS.register(bus);
	}
	
	// @SubscribeEvent
	// public static void register(Register<IRecipeSerializer<?>> event) {
	// BaseRegistryUtil.getAllGenericRegistryEntriesAndApplyNames(UCoreMain.MODID,
	// IRecipeSerializer.class).forEach(event.getRegistry()::register);
	// CraftingHelper.register(new ResourceLocation(UCoreMain.MODID, "item"), ItemIngredient.Serializer.INSTANCE);
	// }
	
}

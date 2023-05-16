package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.ingredient.ItemIngredient;
import info.u_team.u_team_core.intern.recipe.DyeableItemDyeRecipe;
import info.u_team.u_team_core.intern.recipe.NoMirrorShapedRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegisterEvent;

public class UCoreRecipeSerializers {
	
	public static final CommonRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = CommonRegister.create(Registries.RECIPE_SERIALIZER, UCoreMod.MODID);
	
	public static final RegistryEntry<SimpleCraftingRecipeSerializer<DyeableItemDyeRecipe>> CRAFTING_SPECIAL_ITEMDYE = RECIPE_SERIALIZERS.register("crafting_special_itemdye", () -> new SimpleCraftingRecipeSerializer<>(DyeableItemDyeRecipe::new));
	public static final RegistryEntry<NoMirrorShapedRecipe.Serializer> NO_MIRROR_SHAPED = RECIPE_SERIALIZERS.register("crafting_shaped_no_mirror", NoMirrorShapedRecipe.Serializer::new);
	
	private static void registerIngredient(RegisterEvent event) {
		if (event.getRegistryKey().equals(Registries.RECIPE_SERIALIZER)) {
			CraftingHelper.register(new ResourceLocation(UCoreMod.MODID, "item"), ItemIngredient.Serializer.INSTANCE);
		}
	}
	
	public static void registerMod(IEventBus bus) {
		RECIPE_SERIALIZERS.register();
		bus.addListener(UCoreRecipeSerializers::registerIngredient);
	}
}

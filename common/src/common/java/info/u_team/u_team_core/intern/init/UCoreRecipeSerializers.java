package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreReference;
import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.intern.recipe.NoMirrorShapedRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class UCoreRecipeSerializers {
	
	public static final CommonRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = CommonRegister.create(Registries.RECIPE_SERIALIZER, UCoreReference.MODID);
	
	public static final RegistryEntry<NoMirrorShapedRecipe.Serializer> NO_MIRROR_SHAPED = RECIPE_SERIALIZERS.register("crafting_shaped_no_mirror", NoMirrorShapedRecipe.Serializer::new);
	
	static void register() {
		RECIPE_SERIALIZERS.register();
	}
}

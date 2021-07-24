package info.u_team.u_team_core.data.builder;

import java.util.function.Consumer;

import info.u_team.u_team_core.intern.init.UCoreRecipeSerializers;
import info.u_team.u_team_core.util.RecipeBuilderUtil;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public class NoMirrorShapedRecipeBuilder extends ShapedRecipeBuilder {
	
	public NoMirrorShapedRecipeBuilder(ItemLike item, int count) {
		super(item, count);
	}
	
	public static NoMirrorShapedRecipeBuilder noMirrorShapedRecipe(ItemLike item) {
		return noMirrorShapedRecipe(item, 1);
	}
	
	public static NoMirrorShapedRecipeBuilder noMirrorShapedRecipe(ItemLike item, int count) {
		return new NoMirrorShapedRecipeBuilder(item, count);
	}
	
	@Override
	public void save(Consumer<FinishedRecipe> consumer, ResourceLocation location) {
		super.save(recipe -> consumer.accept(RecipeBuilderUtil.getRecipeWithSerializer(recipe, UCoreRecipeSerializers.NO_MIRROR_SHAPED.get())), location);
	}
}

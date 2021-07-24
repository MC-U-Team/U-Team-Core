package info.u_team.u_team_core.data.builder;

import java.util.function.Consumer;

import info.u_team.u_team_core.intern.init.UCoreRecipeSerializers;
import info.u_team.u_team_core.util.RecipeBuilderUtil;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class NoMirrorShapedRecipeBuilder extends ShapedRecipeBuilder {
	
	public NoMirrorShapedRecipeBuilder(IItemProvider item, int count) {
		super(item, count);
	}
	
	public static NoMirrorShapedRecipeBuilder noMirrorShapedRecipe(IItemProvider item) {
		return noMirrorShapedRecipe(item, 1);
	}
	
	public static NoMirrorShapedRecipeBuilder noMirrorShapedRecipe(IItemProvider item, int count) {
		return new NoMirrorShapedRecipeBuilder(item, count);
	}
	
	@Override
	public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation location) {
		super.save(recipe -> consumer.accept(RecipeBuilderUtil.getRecipeWithSerializer(recipe, UCoreRecipeSerializers.NO_MIRROR_SHAPED.get())), location);
	}
}

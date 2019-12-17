package info.u_team.u_team_core.data.builder;

import java.util.function.Consumer;

import info.u_team.u_team_core.intern.init.UCoreRecipeSerializers;
import info.u_team.u_team_core.util.RecipeBuilderUtil;
import net.minecraft.data.*;
import net.minecraft.util.*;

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
	public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation location) {
		super.build(recipe -> consumer.accept(RecipeBuilderUtil.getRecipeWithSerializer(recipe, UCoreRecipeSerializers.NO_MIRROR_SHAPED)), location);
	}
}

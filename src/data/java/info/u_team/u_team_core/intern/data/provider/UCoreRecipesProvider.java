package info.u_team.u_team_core.intern.data.provider;

import java.util.function.Consumer;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.data.CommonRecipesProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_core.intern.init.UCoreRecipeSerializers;
import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.IFinishedRecipe;

public class UCoreRecipesProvider extends CommonRecipesProvider {
	
	public UCoreRecipesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		CustomRecipeBuilder.special(UCoreRecipeSerializers.CRAFTING_SPECIAL_ITEMDYE.get()).save(consumer, UCoreMod.MODID + ":custom_dyeable_item");
	}
	
}

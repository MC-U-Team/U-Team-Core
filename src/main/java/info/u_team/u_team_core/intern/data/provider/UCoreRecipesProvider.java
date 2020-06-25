package info.u_team.u_team_core.intern.data.provider;

import java.util.function.Consumer;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.data.*;
import info.u_team.u_team_core.intern.init.UCoreRecipeSerializers;
import net.minecraft.data.*;

public class UCoreRecipesProvider extends CommonRecipesProvider {
	
	public UCoreRecipesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		CustomRecipeBuilder.customRecipe(UCoreRecipeSerializers.CRAFTING_SPECIAL_ITEMDYE.get()).build(consumer, UCoreMain.MODID + ":custom_dyeable_item");
	}
	
}

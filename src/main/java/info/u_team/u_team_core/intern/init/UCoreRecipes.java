package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.intern.recipe.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.*;

public class UCoreRecipes {
	
	public static final IRecipeSerializer<RecipeShapedCopyNBT> CRAFTING_SPECIAL_COPY_NBT = new RecipeShapedCopyNBT.Serializer(UCoreMain.modid + ":crafting_special_copy_nbt");
	
	public static final IIngredientSerializer<IngredientCopyNBT> INGREDIENT_COPY_NBT = new IngredientCopyNBT.Serializer();
	
	public static void construct() {
		//RecipeSerializers.register(CRAFTING_SPECIAL_COPY_NBT);
		//CraftingHelper.register(new ResourceLocation(UCoreMain.modid, "copy_nbt"), INGREDIENT_COPY_NBT);
	}
	
}

package info.u_team.u_team_core.recipeserializer;

import net.minecraft.item.crafting.*;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class URecipeSerializer<T extends IRecipe<?>> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
	
}

package info.u_team.u_team_core.recipetype;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public record URecipeType<T extends Recipe<?>>(ResourceLocation id) implements RecipeType<T> {
}

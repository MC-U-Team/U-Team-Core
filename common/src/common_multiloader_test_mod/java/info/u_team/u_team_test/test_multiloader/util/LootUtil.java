package info.u_team.u_team_test.test_multiloader.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class LootUtil {
	
	public static ItemStack trySmeltItem(ItemStack stack, Level level) {
		final SingleRecipeInput input = new SingleRecipeInput(stack);
		
		return level.getRecipeManager() //
				.getRecipeFor(RecipeType.SMELTING, input, level) //
				.map(holder -> holder.value()) //
				.map(recipe -> recipe.assemble(input, level.registryAccess())) //
				.filter(itemStack -> !itemStack.isEmpty()) //
				.map(itemStack -> {
					itemStack.setCount(stack.getCount() * itemStack.getCount());
					return itemStack;
				}) //
				.orElse(stack);
	}
	
}

package info.u_team.u_team_test.test_multiloader.util;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class LootUtil {
	
	public static ItemStack trySmeltItem(ItemStack stack, Level level) {
		final SimpleContainer container = new SimpleContainer(stack);
		
		return level.getRecipeManager() //
				.getRecipeFor(RecipeType.SMELTING, container, level) //
				.map(holder -> holder.value())//
				.map(recipe -> recipe.assemble(container, level.registryAccess())) //
				.filter(itemStack -> !itemStack.isEmpty()) //
				.map(itemStack -> {
					itemStack.setCount(stack.getCount() * itemStack.getCount());
					return itemStack;
				}) //
				.orElse(stack);
	}
	
}

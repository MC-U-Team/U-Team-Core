package info.u_team.u_team_test.test_multiloader.util;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.storage.loot.LootContext;

public class LootUtil {
	
	public static ItemStack trySmeltItem(ItemStack stack, LootContext context) {
		final SimpleContainer container = new SimpleContainer(stack);
		
		return context.getLevel() //
				.getRecipeManager() //
				.getRecipeFor(RecipeType.SMELTING, container, context.getLevel()) //
				.map(recipe -> recipe.assemble(container, context.getLevel().registryAccess())) //
				.filter(itemStack -> !itemStack.isEmpty()) //
				.map(itemStack -> {
					itemStack.setCount(stack.getCount() * itemStack.getCount());
					return itemStack;
				}) //
				.orElse(stack);
	}
	
}

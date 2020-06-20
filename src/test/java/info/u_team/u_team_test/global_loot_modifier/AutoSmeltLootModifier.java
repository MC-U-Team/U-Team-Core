package info.u_team.u_team_test.global_loot_modifier;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.*;
import net.minecraftforge.items.ItemHandlerHelper;

public class AutoSmeltLootModifier extends LootModifier {
	
	private AutoSmeltLootModifier(ILootCondition[] conditions) {
		super(conditions);
	}
	
	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		return generatedLoot.stream().map(stack -> smeltItem(stack, context)).collect(Collectors.toList());
	}
	
	private static ItemStack smeltItem(ItemStack stack, LootContext context) {
		return context.getWorld().getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(stack), context.getWorld()).map(FurnaceRecipe::getRecipeOutput).filter(itemStack -> !itemStack.isEmpty()).map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount())).orElse(stack);
	}
	
	public static class Serializer extends GlobalLootModifierSerializer<AutoSmeltLootModifier> {
		
		@Override
		public AutoSmeltLootModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditions) {
			return new AutoSmeltLootModifier(conditions);
		}
	}
	
}

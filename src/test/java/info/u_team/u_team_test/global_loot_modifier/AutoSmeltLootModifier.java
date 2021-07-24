package info.u_team.u_team_test.global_loot_modifier;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

public class AutoSmeltLootModifier extends LootModifier {
	
	public AutoSmeltLootModifier(ILootCondition[] conditions) {
		super(conditions);
	}
	
	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		return generatedLoot.stream().map(stack -> smeltItem(stack, context)).collect(Collectors.toList());
	}
	
	private static ItemStack smeltItem(ItemStack stack, LootContext context) {
		return context.getWorld() //
				.getRecipeManager() //
				.getRecipe(IRecipeType.SMELTING, new Inventory(stack), context.getWorld()) //
				.map(FurnaceRecipe::getRecipeOutput).filter(itemStack -> !itemStack.isEmpty()) //
				.map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount())) //
				.orElse(stack);
	}
	
	public static class Serializer extends GlobalLootModifierSerializer<AutoSmeltLootModifier> {
		
		@Override
		public AutoSmeltLootModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditions) {
			return new AutoSmeltLootModifier(conditions);
		}
		
		@Override
		public JsonObject write(AutoSmeltLootModifier instance) {
			return makeConditions(instance.conditions);
		}
	}
	
}

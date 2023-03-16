package info.u_team.u_team_test.global_loot_modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class AutoSmeltLootModifier extends LootModifier {
	
	public static final Codec<AutoSmeltLootModifier> CODEC = RecordCodecBuilder.create(instance -> LootModifier.codecStart(instance).apply(instance, AutoSmeltLootModifier::new));
	
	public AutoSmeltLootModifier(LootItemCondition[] conditions) {
		super(conditions);
	}
	
	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		return generatedLoot.stream().map(stack -> smeltItem(stack, context)).collect(ObjectArrayList.toList());
	}
	
	private static ItemStack smeltItem(ItemStack stack, LootContext context) {
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
	
	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC;
	}
	
}

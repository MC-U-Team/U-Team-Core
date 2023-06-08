package info.u_team.u_team_core.global_loot_modifier;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;

public abstract class LootItemFunctionLootModifier extends LootModifier {
	
	private final LootItemFunction function;
	
	public LootItemFunctionLootModifier(LootItemCondition[] conditions, LootItemFunction function) {
		super(conditions);
		this.function = function;
	}
	
	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		return generatedLoot.stream().map(stack -> function.apply(stack, context)).collect(ObjectArrayList.toList());
	}
}

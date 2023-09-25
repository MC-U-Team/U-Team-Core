package info.u_team.u_team_test.test_multiloader.loot_item_function;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderLootItemFunctions;
import info.u_team.u_team_test.test_multiloader.util.LootUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class TestLootItemFunction extends LootItemConditionalFunction {
	
	public static final Codec<TestLootItemFunction> CODEC = RecordCodecBuilder.create(instance -> {
		return commonFields(instance).apply(instance, TestLootItemFunction::new);
	});
	
	public static LootItemConditionalFunction.Builder<?> builder() {
		return simpleBuilder(TestLootItemFunction::new);
	}
	
	private TestLootItemFunction(List<LootItemCondition> conditions) {
		super(conditions);
	}
	
	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		if (stack.isEmpty()) {
			return stack;
		}
		return LootUtil.trySmeltItem(stack, context.getLevel());
	}
	
	@Override
	public LootItemFunctionType getType() {
		return TestMultiLoaderLootItemFunctions.TEST.get();
	}
}

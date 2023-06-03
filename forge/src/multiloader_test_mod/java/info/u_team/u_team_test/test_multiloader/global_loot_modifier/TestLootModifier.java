package info.u_team.u_team_test.test_multiloader.global_loot_modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import info.u_team.u_team_test.test_multiloader.loot_item_function.TestLootItemFunction;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class TestLootModifier extends LootModifier {
	
	public static final Codec<TestLootModifier> CODEC = RecordCodecBuilder.create(instance -> LootModifier.codecStart(instance).apply(instance, TestLootModifier::new));
	
	private final LootItemFunction function;
	
	public TestLootModifier(LootItemCondition[] conditions) {
		super(conditions);
		function = TestLootItemFunction.builder().build();
	}
	
	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		return generatedLoot.stream().map(stack -> function.apply(stack, context)).collect(ObjectArrayList.toList());
	}
	
	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC;
	}
	
}

package info.u_team.u_team_test.test_multiloader.global_loot_modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import info.u_team.u_team_core.global_loot_modifier.LootItemFunctionLootModifier;
import info.u_team.u_team_test.test_multiloader.loot_item_function.TestLootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class TestLootModifier extends LootItemFunctionLootModifier {
	
	public static final Codec<TestLootModifier> CODEC = RecordCodecBuilder.create(instance -> LootModifier.codecStart(instance).apply(instance, TestLootModifier::new));
	
	public TestLootModifier(LootItemCondition[] conditions) {
		super(conditions, TestLootItemFunction.builder().build());
	}
	
	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC;
	}
}

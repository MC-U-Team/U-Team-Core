package info.u_team.u_team_test.data.provider;

import java.util.function.Supplier;

import info.u_team.u_team_core.data.CommonGlobalLootModifierProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_core.util.TriConsumer;
import info.u_team.u_team_test.global_loot_modifier.AutoSmeltLootModifier;
import info.u_team.u_team_test.init.TestEnchantments;
import info.u_team.u_team_test.init.TestGlobalLootModifierSerializers;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.IGlobalLootModifier;

public class TestGlobalLootModifierProvider extends CommonGlobalLootModifierProvider {
	
	public TestGlobalLootModifierProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register(TriConsumer<String, Supplier<? extends GlobalLootModifierSerializer<? extends IGlobalLootModifier>>, ? super IGlobalLootModifier> consumer) {
		consumer.accept("auto_smelt", TestGlobalLootModifierSerializers.AUTO_SMELT, new AutoSmeltLootModifier(new LootItemCondition[] { MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(TestEnchantments.AUTO_SMELT.get(), MinMaxBounds.Ints.atLeast(1)))).build() }));
	}
}

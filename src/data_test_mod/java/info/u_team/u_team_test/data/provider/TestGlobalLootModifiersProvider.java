package info.u_team.u_team_test.data.provider;

import java.util.function.Supplier;

import info.u_team.u_team_core.data.CommonGlobalLootModifiersProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_core.util.TriConsumer;
import info.u_team.u_team_test.global_loot_modifier.AutoSmeltLootModifier;
import info.u_team.u_team_test.init.TestEnchantments;
import info.u_team.u_team_test.init.TestGlobalLootModifiers;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.IGlobalLootModifier;

public class TestGlobalLootModifiersProvider extends CommonGlobalLootModifiersProvider {
	
	public TestGlobalLootModifiersProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerGlobalLootModifiers(TriConsumer<String, Supplier<? extends GlobalLootModifierSerializer<? extends IGlobalLootModifier>>, ? super IGlobalLootModifier> consumer) {
		consumer.accept("auto_smelt", TestGlobalLootModifiers.AUTO_SMELT, new AutoSmeltLootModifier(new ILootCondition[] { MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(TestEnchantments.AUTO_SMELT.get(), MinMaxBounds.IntBound.atLeast(1)))).build() }));
	}
}

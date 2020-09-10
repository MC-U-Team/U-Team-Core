package info.u_team.u_team_test.data.provider;

import java.util.function.Supplier;

import info.u_team.u_team_core.data.*;
import info.u_team.u_team_core.util.TriConsumer;
import info.u_team.u_team_test.global_loot_modifier.AutoSmeltLootModifier;
import info.u_team.u_team_test.init.*;
import net.minecraft.advancements.criterion.*;
import net.minecraft.loot.conditions.*;
import net.minecraftforge.common.loot.*;

public class TestGlobalLootModifiersProvider extends CommonGlobalLootModifiersProvider {
	
	public TestGlobalLootModifiersProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerGlobalLootModifiers(TriConsumer<String, Supplier<? extends GlobalLootModifierSerializer<? extends IGlobalLootModifier>>, ? super IGlobalLootModifier> consumer) {
		consumer.accept("auto_smelt", TestGlobalLootModifiers.AUTO_SMELT, new AutoSmeltLootModifier(new ILootCondition[] { MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(TestEnchantments.AUTO_SMELT.get(), MinMaxBounds.IntBound.atLeast(1)))).build() }));
	}
}

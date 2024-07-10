package info.u_team.u_team_test.test_multiloader.forge.data.provider;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import info.u_team.u_team_core.data.CommonGlobalLootModifierProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.forge.global_loot_modifier.TestLootModifier;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderEnchantments;
import info.u_team.u_team_test.test_multiloader.loot_item_condition.TestEnchantmentLootItemCondition;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.RegistrySetBuilder.PatchedRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.RegistryPatchGenerator;

public class ForgeTestMultiLoaderGlobalLootModifierProvider extends CommonGlobalLootModifierProvider {
	
	public ForgeTestMultiLoaderGlobalLootModifierProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public <U> CompletableFuture<U> withRegistries(Function<HolderLookup.Provider, ? extends CompletionStage<U>> function) {
		// Dummy holder lookup with enchantment as it will else not find / load our registries. Not sure how there is an elegant
		// fix other than what vanilla does with its bootstrap methods ...
		return RegistryPatchGenerator.createLookup(getGenerationData().registriesFuture(), new RegistrySetBuilder().add(Registries.ENCHANTMENT, context -> {
			context.register(TestMultiLoaderEnchantments.TEST, null);
		})).thenApply(PatchedRegistries::full).thenCompose(function);
	}
	
	@Override
	public void register(GlobalLootModifierRegister register) {
		register.register("test_enchantment_modifier", new TestLootModifier(TestEnchantmentLootItemCondition.create(register.registries()).build()));
	}
}

package info.u_team.u_team_test.test_multiloader.data.provider;

import java.util.Set;
import java.util.function.Consumer;

import info.u_team.u_team_core.data.CommonDatapackBuiltinEntriesProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderDamageSources;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderEnchantments;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public class TestMultiLoaderDatapackBuiltinEntriesProvider extends CommonDatapackBuiltinEntriesProvider {
	
	public TestMultiLoaderDatapackBuiltinEntriesProvider(GenerationData generationData) {
		super(generationData);
	}
	
	@Override
	public void register(Consumer<DatapackBuiltinEntriesProvider> consumer) {
		consumer.accept(new DatapackBuiltinEntriesProvider(getGenerationData().output(), getGenerationData().registriesFuture(), new RegistrySetBuilder().add(Registries.DAMAGE_TYPE, context -> {
			context.register(TestMultiLoaderDamageSources.TEST, new DamageType("test", 0));
		}), Set.of(modid())));
		
		consumer.accept(new DatapackBuiltinEntriesProvider(getGenerationData().output(), getGenerationData().registriesFuture(), new RegistrySetBuilder().add(Registries.ENCHANTMENT, context -> {
			context.register(TestMultiLoaderEnchantments.TEST, Enchantment.enchantment( //
					Enchantment.definition( //
							context.lookup(Registries.ITEM).getOrThrow(ItemTags.MINING_ENCHANTABLE), //
							1, //
							1, //
							Enchantment.dynamicCost(1, 11), // FSSS
							Enchantment.dynamicCost(12, 11), //
							1, //
							EquipmentSlotGroup.MAINHAND) //
			).build(TestMultiLoaderEnchantments.TEST.location()));
		}), Set.of(modid())));
	}
	
}

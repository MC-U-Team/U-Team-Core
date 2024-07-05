package info.u_team.u_team_test.test_multiloader.data.provider;

import java.util.Set;
import java.util.function.Consumer;

import info.u_team.u_team_core.data.CommonDatapackBuiltinEntriesProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderDamageSources;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;
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
	}
	
}

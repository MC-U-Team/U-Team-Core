package info.u_team.u_team_test.data.provider;

import java.util.function.Supplier;

import info.u_team.u_team_core.data.*;
import info.u_team.u_team_core.util.TriConsumer;
import net.minecraftforge.common.loot.*;

public class TestGlobalLootModifiersProvider extends CommonGlobalLootModifiersProvider {
	
	public TestGlobalLootModifiersProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected <T extends IGlobalLootModifier> void registerGlobalLootModifiers(TriConsumer<String, Supplier<GlobalLootModifierSerializer<T>>, T> consumer) {
	}
	
}

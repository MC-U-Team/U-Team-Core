package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.DataComponentTypeRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

public class TestMultiLoaderDataComponentTypes {
	
	public static final DataComponentTypeRegister DATA_COMPONENT_TYPES = DataComponentTypeRegister.create(TestMultiLoaderReference.MODID);
	
	public static final RegistryEntry<DataComponentType<Integer>> COUNTER_COMPONENT = DATA_COMPONENT_TYPES.register("counter", () -> {
		return DataComponentType.<Integer> builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT);
	});
	
	static void register() {
		DATA_COMPONENT_TYPES.register();
	}
	
}

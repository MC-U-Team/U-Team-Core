package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.client.ColorProviderRegister;
import net.minecraft.Util;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

public class TestColors {
	
	private static final ColorProviderRegister COLORS = Util.make(ColorProviderRegister.create(), colors -> {
		colors.register((itemColors, blockColors, colorRegister) -> colorRegister.register((stack, index) -> {
			if (index == 1) {
				return IClientFluidTypeExtensions.of(TestFluids.TEST_FLUID.get()).getTintColor();
			} else {
				return -1;
			}
		}, TestItems.TEST_FLUID_BUCKET.get()));
	});
	
	static void register() {
		COLORS.register();
	}
}

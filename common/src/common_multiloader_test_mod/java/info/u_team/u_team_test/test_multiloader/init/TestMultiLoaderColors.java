package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.client.ColorProviderRegister;
import net.minecraft.Util;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

public class TestMultiLoaderColors {
	
	private static final ColorProviderRegister COLORS = Util.make(ColorProviderRegister.create(), colors -> {
		colors.register((itemColors, blockColors, colorRegister) -> colorRegister.register((stack, index) -> {
			return FastColor.ARGB32.color(0x80, Mth.hsvToRgb((float) stack.getDamageValue() / (float) stack.getMaxDamage(), 0.8F, 0.5F));
		}, TestMultiLoaderItems.TEST_USE.get()));
	});
	
	static void register() {
		COLORS.register();
	}
}

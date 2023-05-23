package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.api.dye.DyeableItem;
import info.u_team.u_team_core.api.dye.DyeableItemsRegistry;
import info.u_team.u_team_core.api.registry.ColorProviderRegister;
import net.minecraft.Util;

public class UCoreColors {
	
	private static final ColorProviderRegister COLORS = Util.make(ColorProviderRegister.create(), colors -> {
		colors.register((itemColors, blockColors, colorRegister) -> colorRegister.register((stack, index) -> {
			if (stack.getItem() instanceof final DyeableItem dyeable) {
				return dyeable.getColor(stack);
			}
			return 0;
		}, DyeableItemsRegistry.getDyeableItems().stream()));
	});
	
	static void register() {
		COLORS.register();
	}
}

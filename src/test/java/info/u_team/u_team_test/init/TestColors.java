package info.u_team.u_team_test.init;

import java.awt.Color;

import info.u_team.u_team_core.util.registry.ColorsRegistry;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class TestColors {
	
	public static void construct() {
		ColorsRegistry.register((itemstack, index) -> {
			return Color.getHSBColor((float) itemstack.getDamage() / (float) itemstack.getMaxDamage(), 0.8F, 0.5F).getRGB();
		}, TestItems.basic);
	}
	
}

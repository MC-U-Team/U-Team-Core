package info.u_team.u_team_test.init;

import java.awt.Color;

import info.u_team.u_team_test.TestMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public class TestColors {
	
	@SubscribeEvent
	public static void register(ColorHandlerEvent.Item event) {
		// This use the awt color class. Might not work but was too lazy to write an own color class.
		event.getItemColors().register((stack, index) -> Color.getHSBColor((float) stack.getDamage() / (float) stack.getMaxDamage(), 0.8F, 0.5F).getRGB(), TestItems.BASIC);
	}
}

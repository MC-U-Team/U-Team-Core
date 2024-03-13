package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.ClientRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.entity.BetterEnderPearlEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class TestModels {
	
	@SubscribeEvent
	public static void register(FMLClientSetupEvent event) {
		ClientRegistry.registerEntityRenderer(BetterEnderPearlEntity.class, manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
	}
}

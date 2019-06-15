package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.ClientRegistry;
import info.u_team.u_team_test.entity.BetterEnderPearlEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class TestModels {
	
	public static void setup() {
		ClientRegistry.registerEntityRenderer(BetterEnderPearlEntity.class, manager -> {
			return new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer());
		});
	}
}

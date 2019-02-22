package info.u_team.u_team_test.init;

import info.u_team.u_team_core.registry.util.ClientRegistry;
import info.u_team.u_team_test.entity.EntityBetterEnderPearl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSprite;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class TestModels {
	
	public static void setup() {
		ClientRegistry.registerEntityRenderer(EntityBetterEnderPearl.class, manager -> {
			return new RenderSprite<>(manager, TestItems.better_enderpearl, Minecraft.getInstance().getItemRenderer());
		});
	}
}

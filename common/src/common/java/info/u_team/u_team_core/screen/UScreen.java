package info.u_team.u_team_core.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.PerspectiveRenderable;
import info.u_team.u_team_core.api.gui.RenderTickable;
import info.u_team.u_team_core.api.gui.TooltipRenderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class UScreen extends Screen implements PerspectiveRenderable, TooltipRenderable {
	
	public UScreen(Component title) {
		super(title);
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		renderBackground(poseStack, mouseX, mouseY, partialTick);
		super.render(poseStack, mouseX, mouseY, partialTick);
		renderForeground(poseStack, mouseX, mouseY, partialTick);
		renderTooltip(poseStack, mouseX, mouseX, mouseY);
	}
	
	@Override
	public void renderBackground(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		renderBackground(poseStack);
	}
	
	@Override
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
	}
	
	@Override
	public void renderTooltip(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
	}
	
	@Override
	public void tick() {
		for (final GuiEventListener listener : children()) {
			if (listener instanceof final RenderTickable tickable) {
				tickable.renderTick();
			}
		}
	}
}

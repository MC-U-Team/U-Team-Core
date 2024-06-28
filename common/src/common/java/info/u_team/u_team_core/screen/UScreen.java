package info.u_team.u_team_core.screen;

import info.u_team.u_team_core.api.gui.PerspectiveRenderable;
import info.u_team.u_team_core.api.gui.RenderTickable;
import info.u_team.u_team_core.api.gui.TooltipRenderable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class UScreen extends Screen implements PerspectiveRenderable, TooltipRenderable {
	
	public UScreen(Component title) {
		super(title);
	}
	
	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		renderBehind(guiGraphics, mouseX, mouseY, partialTick);
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		renderBefore(guiGraphics, mouseX, mouseY, partialTick);
		renderTooltip(guiGraphics, mouseX, mouseX, mouseY);
	}
	
	@Override
	public void renderBehind(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
	}
	
	@Override
	public void renderBefore(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
	}
	
	@Override
	public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
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

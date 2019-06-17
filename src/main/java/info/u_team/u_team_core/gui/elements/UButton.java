package info.u_team.u_team_core.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.fml.client.config.GuiUtils;

public class UButton extends Button {
	
	public UButton(int x, int y, int width, int height, String displayString, IPressable pessable) {
		super(x, y, width, height, displayString, pessable);
	}
	
	@Override
	public void renderButton(int mouseX, int mouseY, float partialTicks) {
		Minecraft mc = Minecraft.getInstance();
		int k = this.getYImage(isHovered());
		GuiUtils.drawContinuousTexturedBox(WIDGETS_LOCATION, this.x, this.y, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, alpha);
		this.renderBg(mc, mouseX, mouseY);
		
		int color = getFGColor();
		
		String buttonText = this.getMessage();
		int strWidth = mc.fontRenderer.getStringWidth(buttonText);
		int ellipsisWidth = mc.fontRenderer.getStringWidth("...");
		
		if (strWidth > width - 6 && strWidth > ellipsisWidth)
			buttonText = mc.fontRenderer.trimStringToWidth(buttonText, width - 6 - ellipsisWidth).trim() + "...";
		
		this.drawCenteredString(mc.fontRenderer, buttonText, this.x + this.width / 2, this.y + (this.height - 8) / 2, color);
	}
	
}

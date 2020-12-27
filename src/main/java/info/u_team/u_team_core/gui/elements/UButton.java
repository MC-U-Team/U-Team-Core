package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.client.gui.GuiUtils;

/**
 * A button that fixes vanilla not drawing the continuous border if the button is smaller than 20.
 * 
 * @author HyCraftHD
 */
public class UButton extends Button {
	
	protected static IPressable EMTPY_PRESSABLE = button -> {
	};
	
	protected static ITooltip EMPTY_TOOLTIP = field_238486_s_;
	
	public UButton(int x, int y, int width, int height, ITextComponent display) {
		this(x, y, width, height, display, EMTPY_PRESSABLE);
	}
	
	public UButton(int x, int y, int width, int height, ITextComponent display, IPressable pessable) {
		this(x, y, width, height, display, pessable, EMPTY_TOOLTIP);
	}
	
	public UButton(int x, int y, int width, int height, ITextComponent display, IPressable pessable, ITooltip tooltip) {
		super(x, y, width, height, display, pessable);
	}
	
	public void setPressable(IPressable pressable) {
		onPress = pressable;
	}
	
	public void setPressable(Runnable runnable) {
		onPress = button -> runnable.run();
	}
	
	public void setTooltip(ITooltip tooltip) {
		onTooltip = tooltip;
	}
	
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		final Minecraft minecraft = Minecraft.getInstance();
		final FontRenderer fontRenderer = minecraft.fontRenderer;
		
		ITextComponent message = getMessage();
		
		GuiUtils.drawContinuousTexturedBox(matrixStack, WIDGETS_LOCATION, x, y, 0, 46 + getYImage(isHovered()) * 20, width, height, 200, 20, 2, 3, 2, 2, 0);
		renderBg(matrixStack, minecraft, mouseX, mouseY);
		
		final int messageWidth = fontRenderer.getStringPropertyWidth(message);
		final int ellipsisWidth = fontRenderer.getStringWidth("...");
		
		if (messageWidth > width - 6 && messageWidth > ellipsisWidth) {
			message = new StringTextComponent(fontRenderer.func_238417_a_(message, width - 6 - ellipsisWidth).getString() + "...");
		}
		
		drawCenteredString(matrixStack, fontRenderer, message, x + width / 2, y + (height - 8) / 2, getFGColor());
	}
}

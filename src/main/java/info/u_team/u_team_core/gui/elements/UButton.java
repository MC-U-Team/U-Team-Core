package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

/**
 * A button that fixes vanilla not drawing the continuous border if the button is smaller than 20 The code is adapted
 * from the 1.13.2 GuiButtonExt.
 * 
 * @author HyCraftHD
 */
public class UButton extends Button {
	
	protected static IPressable EMTPY_PRESSABLE = button -> {
	};
	
	public UButton(int x, int y, int width, int height, ITextComponent display) {
		super(x, y, width, height, display, EMTPY_PRESSABLE);
	}
	
	public UButton(int x, int y, int width, int height, ITextComponent display, IPressable pessable) {
		super(x, y, width, height, display, pessable);
	}
	
	public void setPressable(IPressable pressable) {
		onPress = pressable;
	}
	
	public void setPressable(Runnable runnable) {
		onPress = button -> runnable.run();
	}
	
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		final Minecraft minecraft = Minecraft.getInstance();
		final FontRenderer fontRenderer = minecraft.fontRenderer;
		
		String message = getMessage().getString();
		
		GuiUtils.drawContinuousTexturedBox(matrixStack, WIDGETS_LOCATION, x, y, 0, 46 + getYImage(isHovered()) * 20, width, height, 200, 20, 2, 3, 2, 2, 0);
		renderBg(matrixStack, minecraft, mouseX, mouseY);
		
		final int messageWidth = fontRenderer.getStringWidth(message);
		final int ellipsisWidth = fontRenderer.getStringWidth("...");
		
		if (messageWidth > width - 6 && messageWidth > ellipsisWidth) {
			message = fontRenderer.func_238412_a_(message, width - 6 - ellipsisWidth).trim() + "...";
		}
		
		drawCenteredString(matrixStack, fontRenderer, message, x + width / 2, y + (height - 8) / 2, getFGColor());
	}
}
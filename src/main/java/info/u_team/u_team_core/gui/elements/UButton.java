package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.*;
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
		field_230697_t_ = pressable;
	}
	
	public void setPressable(Runnable runnable) {
		field_230697_t_ = button -> runnable.run();
	}
	
	@Override
	public void func_230431_b_(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		final Minecraft minecraft = Minecraft.getInstance();
		final FontRenderer fontRenderer = minecraft.fontRenderer;
		
		String message = func_230458_i_().getString();
		
		GuiUtils.drawContinuousTexturedBox(field_230687_i_, field_230690_l_, field_230691_m_, 0, 46 + func_230989_a_(func_230449_g_()) * 20, field_230688_j_, field_230689_k_, 200, 20, 2, 3, 2, 2, 0);
		func_230441_a_(matrixStack, minecraft, mouseX, mouseY);
		
		final int messageWidth = fontRenderer.getStringWidth(message);
		final int ellipsisWidth = fontRenderer.getStringWidth("...");
		
		if (messageWidth > field_230688_j_ - 6 && messageWidth > ellipsisWidth) {
			message = fontRenderer.func_238412_a_(message, field_230688_j_ - 6 - ellipsisWidth).trim() + "...";
		}
		
		func_238471_a_(matrixStack, fontRenderer, message, field_230690_l_ + field_230688_j_ / 2, field_230691_m_ + (field_230689_k_ - 8) / 2, getFGColor());
	}
}

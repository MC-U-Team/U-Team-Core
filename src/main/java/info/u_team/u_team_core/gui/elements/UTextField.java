package info.u_team.u_team_core.gui.elements;

import info.u_team.u_team_core.api.gui.IRenderTickable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;

public class UTextField extends TextFieldWidget implements IRenderTickable {
	
	public UTextField(FontRenderer fontRenderer, int x, int y, int width, int height, TextFieldWidget previousTextField, ITextComponent title) {
		super(fontRenderer, x, y, width, height, previousTextField, title);
	}
	
	@Override
	public void renderTick() {
		tick();
	}
	
}

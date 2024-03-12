package info.u_team.u_team_test.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.gui.elements.ScrollableListEntry;

public class BasicTestListEntry extends ScrollableListEntry<BasicTestListEntry> {
	
	@Override
	public void render(MatrixStack matrixStack, int slotIndex, int entryY, int entryX, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float partialTicks) {
		minecraft.fontRenderer.drawString(matrixStack, "Test, Entry!", entryX + 10, entryY + 5, 0xFFFFFF);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		list.setSelected(this);
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
}

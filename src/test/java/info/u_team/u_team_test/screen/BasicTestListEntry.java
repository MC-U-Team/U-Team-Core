package info.u_team.u_team_test.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.gui.elements.ScrollableListEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class BasicTestListEntry extends ScrollableListEntry<BasicTestListEntry> {

	@Override
	public void render(PoseStack matrixStack, int slotIndex, int entryY, int entryX, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float partialTicks) {
		minecraft.font.draw(matrixStack, "Test, Entry!", entryX + 10, entryY + 5, 0xFFFFFF);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		list.setSelected(this);
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public Component getNarration() {
		return TextComponent.EMPTY;
	}

}

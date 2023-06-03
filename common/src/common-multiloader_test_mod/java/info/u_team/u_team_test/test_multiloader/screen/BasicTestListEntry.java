package info.u_team.u_team_test.test_multiloader.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.gui.elements.ScrollableListEntry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class BasicTestListEntry extends ScrollableListEntry<BasicTestListEntry> {
	
	@Override
	public void render(PoseStack poseStack, int slotIndex, int entryY, int entryX, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float partialTick) {
		minecraft.font.draw(poseStack, "Test, Entry!", entryX + 10, entryY + 5, 0xFFFFFF);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		getList().setSelected(this);
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public Component getNarration() {
		return CommonComponents.EMPTY;
	}
	
}

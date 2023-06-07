package info.u_team.u_team_test.test_multiloader.screen;

import info.u_team.u_team_core.gui.elements.UButton;
import info.u_team.u_team_core.gui.elements.USlider;
import info.u_team.u_team_core.screen.UContainerMenuScreen;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.menu.TestInventoryMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class TestInventoryScreen extends UContainerMenuScreen<TestInventoryMenu> {
	
	private static final ResourceLocation BACKGROUND = new ResourceLocation(TestMultiLoaderReference.MODID, "textures/gui/test_inventory.png");
	
	private USlider slider;
	
	public TestInventoryScreen(TestInventoryMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title, BACKGROUND, 176, 173);
	}
	
	@Override
	protected void init() {
		super.init();
		addRenderableWidget(new UButton(leftPos + imageWidth / 2 - 25, topPos + 3, 50, 15, Component.literal("Add 100"), button -> {
			menu.getValueMessage().triggerMessage();
		}));
		
		slider = addRenderableWidget(new USlider(leftPos + 7, topPos + 19, 162, 20, Component.literal("Cooldown: "), Component.literal(" Ticks"), 0, 100, menu.getBlockEntity().getCooldown(), false, true, slider -> {
			menu.getCooldownMessage().triggerMessage(() -> new FriendlyByteBuf(Unpooled.copyShort(slider.getValueInt())));
		}));
	}
	
	@Override
	public void containerTick() {
		super.containerTick();
		if ((!isDragging() || !slider.isHoveredOrFocused()) && minecraft.level.getGameTime() % 20 == 0) {
			slider.setValue(menu.getBlockEntity().getCooldown());
		}
	}
	
	@Override
	protected void renderLabels(GuiGraphics guiGrapics, int mouseX, int mouseY) {
		super.renderLabels(guiGrapics, mouseX, mouseY);
		guiGrapics.drawString(font, Integer.toString(menu.getBlockEntity().getValue()), imageWidth / 2 + 32, 6, 0x404040);
	}
	
}

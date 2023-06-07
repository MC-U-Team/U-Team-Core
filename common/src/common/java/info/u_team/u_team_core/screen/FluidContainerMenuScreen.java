package info.u_team.u_team_core.screen;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class FluidContainerMenuScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
	
	private final FluidContainerScreenDelegator delegator = FluidContainerScreenDelegator.Factory.INSTANCE.create(this);
	
	protected FluidContainerMenuScreen(T container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
	}
	
	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		delegator.renderLabels(guiGraphics, mouseX, mouseY);
	}
	
	@Override
	protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		super.renderTooltip(guiGraphics, mouseX, mouseY);
		delegator.renderTooltip(guiGraphics, mouseX, mouseY);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (delegator.mouseClicked(mouseX, mouseY, button)) {
			return true;
		} else {
			return super.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	public FluidContainerScreenDelegator getDelegator() {
		return delegator;
	}
	
	public static interface FluidContainerScreenDelegator {
		
		void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY);
		
		void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY);
		
		boolean mouseClicked(double mouseX, double mouseY, int button);
		
		interface Factory {
			
			Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
			
			<T extends AbstractContainerMenu> FluidContainerScreenDelegator create(FluidContainerMenuScreen<T> menu);
		}
	}
}

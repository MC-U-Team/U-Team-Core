package info.u_team.u_team_core.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.util.ServiceUtil;
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
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		delegator.renderLabels(poseStack, mouseX, mouseY);
	}
	
	@Override
	protected void renderTooltip(PoseStack poseStack, int mouseX, int mouseY) {
		super.renderTooltip(poseStack, mouseX, mouseY);
		delegator.renderTooltip(poseStack, mouseX, mouseY);
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
		
		void renderLabels(PoseStack poseStack, int mouseX, int mouseY);
		
		void renderTooltip(PoseStack poseStack, int mouseX, int mouseY);
		
		boolean mouseClicked(double mouseX, double mouseY, int button);
		
		interface Factory {
			
			Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
			
			<T extends AbstractContainerMenu> FluidContainerScreenDelegator create(FluidContainerMenuScreen<T> menu);
		}
	}
}

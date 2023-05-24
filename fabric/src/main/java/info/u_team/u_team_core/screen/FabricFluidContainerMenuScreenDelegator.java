package info.u_team.u_team_core.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.screen.FluidContainerMenuScreen.FluidContainerScreenDelegator;
import net.minecraft.world.inventory.AbstractContainerMenu;

// Unstable, Not implemented yet
public class FabricFluidContainerMenuScreenDelegator<T extends AbstractContainerMenu> implements FluidContainerScreenDelegator {
	
	FabricFluidContainerMenuScreenDelegator(FluidContainerMenuScreen<T> screen) {
	}
	
	@Override
	public void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
	}
	
	@Override
	public void renderTooltip(PoseStack poseStack, int mouseX, int mouseY) {
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return false;
	}
	
	public static class Factory implements FluidContainerScreenDelegator.Factory {
		
		@Override
		public <T extends AbstractContainerMenu> FluidContainerScreenDelegator create(FluidContainerMenuScreen<T> menu) {
			return new FabricFluidContainerMenuScreenDelegator<>(menu);
		}
	}
	
}

package info.u_team.u_team_core.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.gui.renderer.FluidInventoryRenderer;
import info.u_team.u_team_core.intern.init.UCoreForgeNetwork;
import info.u_team.u_team_core.intern.network.FluidClickContainerMessage;
import info.u_team.u_team_core.menu.FluidContainerMenu;
import info.u_team.u_team_core.menu.FluidSlot;
import info.u_team.u_team_core.menu.ForgeFluidContainerMenuDelegator;
import info.u_team.u_team_core.screen.FluidContainerMenuScreen.FluidContainerScreenDelegator;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

// Very unstable api, will be reworked
public class ForgeFluidContainerMenuScreenDelegator<T extends AbstractContainerMenu> implements FluidContainerScreenDelegator {
	
	private final FluidContainerMenuScreen<T> screen;
	
	ForgeFluidContainerMenuScreenDelegator(FluidContainerMenuScreen<T> screen) {
		this.screen = screen;
	}
	
	protected FluidInventoryRenderer fluidRenderer = FluidInventoryRenderer.DEFAULT_INSTANCE;
	protected FluidSlot hoveredFluidSlot;
	
	public void setFluidRenderer(FluidInventoryRenderer fluidRenderer) {
		this.fluidRenderer = fluidRenderer;
	}
	
	@Override
	public void renderLabels(GuiGraphics guiGrapics, int mouseX, int mouseY) {
		if (screen.getMenu() instanceof final FluidContainerMenu fluidMenu) {
			hoveredFluidSlot = null;
			
			for (int slot = 0; slot < ((ForgeFluidContainerMenuDelegator) fluidMenu.getDelegator()).fluidSlots.size(); slot++) {
				final FluidSlot fluidSlot = ((ForgeFluidContainerMenuDelegator) fluidMenu.getDelegator()).fluidSlots.get(slot);
				
				if (fluidSlot.isActive()) {
					renderFluidSlot(guiGrapics, fluidSlot);
					
					if (isHovering(fluidSlot, mouseX, mouseY)) {
						hoveredFluidSlot = fluidSlot;
						RenderUtil.setShaderColor(RGBA.WHITE);
						AbstractContainerScreen.renderSlotHighlight(guiGrapics, fluidSlot.getX(), fluidSlot.getY(), 0, getFluidSlotColor(slot));
					}
				}
			}
		}
	}
	
	protected void renderFluidSlot(GuiGraphics guiGrapics, FluidSlot fluidSlot) {
		final PoseStack poseStack = guiGrapics.pose();
		poseStack.pushPose();
		poseStack.translate(0.0F, 0.0F, 100.0F);
		
		final int x = fluidSlot.getX();
		final int y = fluidSlot.getY();
		
		if (!fluidSlot.hasFluid() && fluidSlot.isActive()) {
			final var pair = fluidSlot.getNoItemIcon();
			if (pair != null) {
				final TextureAtlasSprite sprite = screen.getMinecraft().getTextureAtlas(pair.getFirst()).apply(pair.getSecond());
				
				RenderUtil.drawTexturedQuad(poseStack, x, y, 16, 16, 0, sprite, RGBA.WHITE);
			}
		}
		
		fluidRenderer.drawFluidInSlot(poseStack, x, y, 0, fluidSlot.getFluid());
		
		poseStack.popPose();
	}
	
	@Override
	public void renderTooltip(GuiGraphics guiGrapics, int mouseX, int mouseY) {
		if (screen.getMenu().getCarried().isEmpty() && hoveredFluidSlot != null && hoveredFluidSlot.hasFluid()) {
			guiGrapics.renderComponentTooltip(Minecraft.getInstance().font, getTooltipFromFluid(hoveredFluidSlot), mouseX, mouseY);
		}
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0) {
			final FluidSlot fluidSlot = findFluidSlot(mouseX, mouseY);
			if (fluidSlot != null) {
				if (!screen.getMenu().getCarried().isEmpty()) {
					UCoreForgeNetwork.NETWORK.send(new FluidClickContainerMessage(screen.getMenu().containerId, fluidSlot.index, Screen.hasShiftDown(), screen.getMenu().getCarried()), PacketDistributor.SERVER.noArg());
				}
				return true;
			}
		}
		return false;
	}
	
	protected FluidSlot findFluidSlot(double mouseX, double mouseY) {
		if (screen.getMenu() instanceof final FluidContainerMenu fluidMenu) {
			for (final FluidSlot fluidSlot : ((ForgeFluidContainerMenuDelegator) fluidMenu.getDelegator()).fluidSlots) {
				if (isHovering(fluidSlot, mouseX, mouseY) && fluidSlot.isActive()) {
					return fluidSlot;
				}
			}
		}
		return null;
	}
	
	protected boolean isHovering(FluidSlot fluidSlot, double mouseX, double mouseY) {
		return screen.isHovering(fluidSlot.getX(), fluidSlot.getY(), 16, 16, mouseX, mouseY);
	}
	
	protected int getFluidSlotColor(int index) {
		return screen.getSlotColor(index); // TODO was super.getSlotColor
	}
	
	public List<Component> getTooltipFromFluid(FluidSlot fluidSlot) {
		final FluidStack stack = fluidSlot.getFluid();
		
		final List<Component> list = new ArrayList<>();
		
		list.add(stack.getDisplayName());
		list.add(Component.literal(stack.getAmount() + " mb/" + fluidSlot.getSlotCapacity() + " mb").withStyle(ChatFormatting.GRAY));
		
		if (screen.getMinecraft().options.advancedItemTooltips) {
			list.add(Component.literal(ForgeRegistries.FLUIDS.getKey(stack.getFluid()).toString()).withStyle(ChatFormatting.DARK_GRAY));
		}
		
		return list;
	}
	
	public static class Factory implements FluidContainerScreenDelegator.Factory {
		
		@Override
		public <T extends AbstractContainerMenu> FluidContainerScreenDelegator create(FluidContainerMenuScreen<T> menu) {
			return new ForgeFluidContainerMenuScreenDelegator<>(menu);
		}
	}
}

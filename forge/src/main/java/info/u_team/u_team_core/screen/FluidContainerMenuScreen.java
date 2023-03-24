package info.u_team.u_team_core.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.gui.renderer.FluidInventoryRenderer;
import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.FluidClickContainerMessage;
import info.u_team.u_team_core.menu.FluidContainerMenu;
import info.u_team.u_team_core.menu.FluidSlot;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class FluidContainerMenuScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
	
	protected FluidInventoryRenderer fluidRenderer;
	
	protected FluidSlot hoveredFluidSlot;
	
	protected FluidContainerMenuScreen(T container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		fluidRenderer = FluidInventoryRenderer.DEFAULT_INSTANCE;
	}
	
	protected void setFluidRenderer(FluidInventoryRenderer fluidRenderer) {
		this.fluidRenderer = fluidRenderer;
	}
	
	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		if (menu instanceof final FluidContainerMenu fluidMenu) {
			hoveredFluidSlot = null;
			
			for (int slot = 0; slot < fluidMenu.fluidSlots.size(); slot++) {
				final FluidSlot fluidSlot = fluidMenu.fluidSlots.get(slot);
				
				if (fluidSlot.isActive()) {
					renderFluidSlot(poseStack, fluidSlot);
					
					if (isHovering(fluidSlot, mouseX, mouseY)) {
						hoveredFluidSlot = fluidSlot;
						RenderUtil.setShaderColor(RGBA.WHITE);
						renderSlotHighlight(poseStack, fluidSlot.getX(), fluidSlot.getY(), 0, getFluidSlotColor(slot));
					}
				}
			}
		}
	}
	
	protected void renderFluidSlot(PoseStack poseStack, FluidSlot fluidSlot) {
		poseStack.pushPose();
		poseStack.translate(0.0F, 0.0F, 100.0F);
		
		final int x = fluidSlot.getX();
		final int y = fluidSlot.getY();
		
		if (!fluidSlot.hasFluid() && fluidSlot.isActive()) {
			final var pair = fluidSlot.getNoItemIcon();
			if (pair != null) {
				final TextureAtlasSprite sprite = minecraft.getTextureAtlas(pair.getFirst()).apply(pair.getSecond());
				
				RenderUtil.drawTexturedQuad(poseStack, x, y, 16, 16, 0, sprite, RGBA.WHITE);
			}
		}
		
		fluidRenderer.drawFluidInSlot(poseStack, x, y, 0, fluidSlot.getFluid());
		
		poseStack.popPose();
	}
	
	@Override
	protected void renderTooltip(PoseStack poseStack, int mouseX, int mouseY) {
		super.renderTooltip(poseStack, mouseX, mouseY);
		
		if (menu.getCarried().isEmpty() && hoveredFluidSlot != null && hoveredFluidSlot.hasFluid()) {
			renderComponentTooltip(poseStack, getTooltipFromFluid(hoveredFluidSlot), mouseX, mouseY);
		}
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0) {
			final FluidSlot fluidSlot = findFluidSlot(mouseX, mouseY);
			if (fluidSlot != null) {
				if (!menu.getCarried().isEmpty()) {
					UCoreNetwork.NETWORK.sendToServer(new FluidClickContainerMessage(menu.containerId, fluidSlot.index, hasShiftDown(), menu.getCarried()));
				}
				return true;
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	protected FluidSlot findFluidSlot(double mouseX, double mouseY) {
		if (menu instanceof final FluidContainerMenu fluidMenu) {
			for (final FluidSlot fluidSlot : fluidMenu.fluidSlots) {
				if (isHovering(fluidSlot, mouseX, mouseY) && fluidSlot.isActive()) {
					return fluidSlot;
				}
			}
		}
		return null;
	}
	
	protected boolean isHovering(FluidSlot fluidSlot, double mouseX, double mouseY) {
		return isHovering(fluidSlot.getX(), fluidSlot.getY(), 16, 16, mouseX, mouseY);
	}
	
	protected int getFluidSlotColor(int index) {
		return super.getSlotColor(index);
	}
	
	public List<Component> getTooltipFromFluid(FluidSlot fluidSlot) {
		final FluidStack stack = fluidSlot.getFluid();
		
		final List<Component> list = new ArrayList<>();
		
		list.add(stack.getDisplayName());
		list.add(Component.literal(stack.getAmount() + " mb/" + fluidSlot.getSlotCapacity() + " mb").withStyle(ChatFormatting.GRAY));
		
		if (minecraft.options.advancedItemTooltips) {
			list.add(Component.literal(ForgeRegistries.FLUIDS.getKey(stack.getFluid()).toString()).withStyle(ChatFormatting.DARK_GRAY));
		}
		
		return list;
	}
}

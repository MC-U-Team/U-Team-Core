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
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
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
		if (menu instanceof FluidContainerMenu fluidMenu) {
			hoveredFluidSlot = null;
			
			for (var index = 0; index < fluidMenu.fluidSlots.size(); index++) {
				final var fluidSlot = fluidMenu.fluidSlots.get(index);
				
				if (fluidSlot.isActive()) {
					renderFluidSlot(poseStack, fluidSlot);
					
					if (isHovering(fluidSlot, mouseX, mouseY)) {
						hoveredFluidSlot = fluidSlot;
						RenderUtil.setShaderColor(RGBA.WHITE);
						renderSlotHighlight(poseStack, fluidSlot.getX(), fluidSlot.getY(), getBlitOffset(), getFluidSlotColor(index));
					}
				}
			}
		}
	}
	
	protected void renderFluidSlot(PoseStack poseStack, FluidSlot fluidSlot) {
		setBlitOffset(100);
		fluidRenderer.drawFluidInSlot(poseStack, fluidSlot.getX(), fluidSlot.getY(), getBlitOffset(), fluidSlot.getFluid());
		setBlitOffset(0);
	}
	
	@Override
	protected void renderTooltip(PoseStack poseStack, int mouseX, int mouseY) {
		super.renderTooltip(poseStack, mouseX, mouseY);
		
		if (menu.getCarried().isEmpty() && hoveredFluidSlot != null && !hoveredFluidSlot.getFluid().isEmpty()) { // TODO add more methods to fluid slot to make this easier
			renderComponentTooltip(poseStack, getTooltipFromFluid(hoveredFluidSlot), mouseX, mouseY);
		}
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0) {
			final var fluidSlot = findFluidSlot(mouseX, mouseY);
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
		if (menu instanceof FluidContainerMenu fluidMenu) {
			for (final var fluidSlot : fluidMenu.fluidSlots) {
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
		final var stack = fluidSlot.getFluid();
		
		final List<Component> list = new ArrayList<>();
		
		list.add(stack.getDisplayName());
		list.add(new TextComponent(stack.getAmount() + " mb/" + fluidSlot.getSlotCapacity() + " mb").withStyle(ChatFormatting.GRAY));
		
		if (minecraft.options.advancedItemTooltips) {
			list.add(new TextComponent(ForgeRegistries.FLUIDS.getKey(stack.getFluid()).toString()).withStyle(ChatFormatting.DARK_GRAY));
		}
		
		return list;
	}
}

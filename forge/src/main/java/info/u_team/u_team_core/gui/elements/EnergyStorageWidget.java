package info.u_team.u_team_core.gui.elements;

import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.gui.PerspectiveRenderable;
import info.u_team.u_team_core.api.gui.TooltipRenderable;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import info.u_team.u_team_core.util.SiUtil;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyStorageWidget extends AbstractWidget implements PerspectiveRenderable, TooltipRenderable {
	
	public static final ResourceLocation ENERGY_TEXTURE = new ResourceLocation(UCoreMod.MODID, "textures/gui/energy.png");
	
	private final LongSupplier capacity;
	private final LongSupplier storage;
	
	protected ResourceLocation texture;
	protected RGBA color;
	
	public EnergyStorageWidget(int x, int y, int height, Supplier<IEnergyStorage> energyStorage) {
		this(x, y, height, () -> energyStorage.get().getMaxEnergyStored(), () -> energyStorage.get().getEnergyStored());
	}
	
	public EnergyStorageWidget(int x, int y, int height, LongSupplier capacity, LongSupplier storage) {
		super(x, y, 14, height < 3 ? 3 : height, Component.empty());
		this.capacity = capacity;
		this.storage = storage;
		texture = ENERGY_TEXTURE;
		color = RGBA.WHITE;
	}
	
	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		renderBehind(guiGraphics, mouseX, mouseY, partialTick);
		renderBefore(guiGraphics, mouseX, mouseY, partialTick);
		WidgetUtil.renderCustomTooltipForWidget(this, guiGraphics, mouseX, mouseY, partialTick);
	}
	
	@Override
	public void renderBehind(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		double ratio = (double) storage.getAsLong() / capacity.getAsLong();
		if (ratio > 1) {
			ratio = 1;
		}
		
		final int storageOffset = (int) ((1 - ratio) * (height - 2));
		
		final Tesselator tessellator = Tesselator.getInstance();
		final BufferBuilder bufferBuilder = tessellator.getBuilder();
		
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, texture);
		RenderUtil.setShaderColor(color);
		
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		
		for (int yComponent = 1; yComponent < height - 1; yComponent += 2) {
			RenderUtil.addTexturedQuad(bufferBuilder, guiGraphics.pose(), x + 1, x + 1 + 12, y + yComponent, y + yComponent + 2, 0, 12 / 16f, 0, 2 / 16f, 0); // Background
		}
		
		for (int yComponent = 1 + storageOffset; yComponent < height - 1; yComponent++) {
			if (yComponent % 2 == 0) {
				RenderUtil.addTexturedQuad(bufferBuilder, guiGraphics.pose(), x + 1, x + 1 + 12, y + yComponent, y + yComponent + 1, 0, 12 / 16f, 3 / 16f, 4 / 16f, 0); // Background
			} else {
				RenderUtil.addTexturedQuad(bufferBuilder, guiGraphics.pose(), x + 1, x + 1 + 12, y + yComponent, y + yComponent + 1, 0, 12 / 16f, 2 / 16f, 3 / 16f, 0); // Background
			}
		}
		
		tessellator.end();
		
		RenderSystem.disableBlend();
	}
	
	@Override
	public void renderBefore(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		RenderUtil.drawContainerBorder(guiGraphics.pose(), x, y, width, height, 0, RGBA.WHITE);
	}
	
	@Override
	public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		if (isHovered) {
			final Minecraft minecraft = Minecraft.getInstance();
			
			final String storageString, capacityString;
			
			if (!Screen.hasShiftDown()) {
				storageString = SiUtil.readableSi(storage.getAsLong());
				capacityString = SiUtil.readableSi(capacity.getAsLong());
			} else {
				storageString = Long.toString(storage.getAsLong()) + " ";
				capacityString = Long.toString(capacity.getAsLong()) + " ";
			}
			
			final List<Component> list = List.of(Component.translatable("gui.widget.uteamcore.energy.fe_tooltip", storageString, capacityString));
			guiGraphics.renderTooltip(minecraft.font, list, Optional.empty(), mouseX, mouseY);
		}
	}
	
	@Override
	public void playDownSound(SoundManager handler) {
		// Don't play click sound
	}
	
	@Override
	protected void updateWidgetNarration(NarrationElementOutput output) {
		// TODO add narration message
	}
	
}

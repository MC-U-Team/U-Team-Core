package info.u_team.u_team_core.gui.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.util.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fmlclient.gui.GuiUtils;

public class EnergyStorageWidget extends AbstractWidget {
	
	public static final ResourceLocation ENERGY_TEXTURE = new ResourceLocation(UCoreMod.MODID, "textures/gui/energy.png");
	
	private final LongSupplier capacity;
	private final LongSupplier storage;
	
	public EnergyStorageWidget(int x, int y, int height, LazyOptional<IEnergyStorage> energyStorage) {
		this(x, y, height, () -> energyStorage.map(IEnergyStorage::getMaxEnergyStored).orElse(0), () -> energyStorage.map(IEnergyStorage::getEnergyStored).orElse(0));
	}
	
	public EnergyStorageWidget(int x, int y, int height, Supplier<IEnergyStorage> energyStorage) {
		this(x, y, height, () -> energyStorage.get().getMaxEnergyStored(), () -> energyStorage.get().getEnergyStored());
	}
	
	public EnergyStorageWidget(int x, int y, int height, LongSupplier capacity, LongSupplier storage) {
		super(x, y, 14, height < 3 ? 3 : height, Component.nullToEmpty(null));
		this.capacity = capacity;
		this.storage = storage;
	}
	
	@Override
	public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		final var minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bindForSetup(ENERGY_TEXTURE);
		
		var ratio = (double) storage.getAsLong() / capacity.getAsLong();
		if (ratio > 1) {
			ratio = 1;
		}
		
		final var storageOffset = (int) ((1 - ratio) * (height - 2));
		
		for (var yComponent = 1; yComponent < height - 1; yComponent += 2) {
			blit(matrixStack, x + 1, y + yComponent, 0, 0, 12, 2, 16, 16); // Background with side border
		}
		
		for (var yComponent = 1 + storageOffset; yComponent < height - 1; yComponent++) {
			if (yComponent % 2 == 0) {
				blit(matrixStack, x + 1, y + yComponent, 0, 3, 12, 1, 16, 16); // Fuel
			} else {
				blit(matrixStack, x + 1, y + yComponent, 0, 2, 12, 1, 16, 16); // Fuel
			}
		}
		
		GuiUtil.drawContainerBorder(matrixStack, x, y, width, height);
	}
	
	@Override
	public void renderToolTip(PoseStack matrixStack, int mouseX, int mouseY) {
		if (isHovered) {
			final var minecraft = Minecraft.getInstance();
			final var mainWindow = minecraft.getWindow();
			
			final List<Component> list = new ArrayList<>();
			list.add(Component.nullToEmpty(storage.getAsLong() + " / " + capacity.getAsLong() + " FE"));
			
			GuiUtils.drawHoveringText(matrixStack, list, mouseX, mouseY, mainWindow.getScreenWidth(), mainWindow.getScreenHeight(), 300, minecraft.font);
		}
	}
	
	@Override
	public void playDownSound(SoundManager handler) {
		// Don't play click sound
	}
	
	@Override
	public void updateNarration(NarrationElementOutput p_169152_) {
	}
}

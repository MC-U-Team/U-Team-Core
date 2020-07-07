package info.u_team.u_team_core.gui.elements;

import java.util.*;
import java.util.function.*;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.util.GuiUtil;
import net.minecraft.client.*;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.client.gui.GuiUtils;

@OnlyIn(Dist.CLIENT)
public class EnergyStorageWidget extends Widget {
	
	public static final ResourceLocation ENERGY_TEXTURE = new ResourceLocation(UCoreMain.MODID, "textures/gui/energy.png");
	
	private final LongSupplier capacity;
	private final LongSupplier storage;
	
	public EnergyStorageWidget(int x, int y, int height, LazyOptional<IEnergyStorage> energyStorage) {
		this(x, y, height, () -> energyStorage.map(IEnergyStorage::getMaxEnergyStored).orElse(0), () -> energyStorage.map(IEnergyStorage::getEnergyStored).orElse(0));
	}
	
	public EnergyStorageWidget(int x, int y, int height, Supplier<IEnergyStorage> energyStorage) {
		this(x, y, height, () -> energyStorage.get().getMaxEnergyStored(), () -> energyStorage.get().getEnergyStored());
	}
	
	public EnergyStorageWidget(int x, int y, int height, LongSupplier capacity, LongSupplier storage) {
		super(x, y, 14, height < 3 ? 3 : height, ITextComponent.func_241827_a_(null));
		this.capacity = capacity;
		this.storage = storage;
	}
	
	@Override
	public void func_230431_b_(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		final Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bindTexture(ENERGY_TEXTURE);
		
		double ratio = (double) storage.getAsLong() / capacity.getAsLong();
		if (ratio > 1) {
			ratio = 1;
		}
		
		final int storageOffset = (int) ((1 - ratio) * (field_230689_k_ - 2));
		
		for (int yComponent = 1; yComponent < field_230689_k_ - 1; yComponent += 2) {
			func_238463_a_(matrixStack, field_230690_l_ + 1, field_230691_m_ + yComponent, 0, 0, 12, 2, 16, 16); // Background with side border
		}
		
		for (int yComponent = 1 + storageOffset; yComponent < field_230689_k_ - 1; yComponent++) {
			if (yComponent % 2 == 0) {
				func_238463_a_(matrixStack, field_230690_l_ + 1, field_230691_m_ + yComponent, 0, 3, 12, 1, 16, 16); // Fuel
			} else {
				func_238463_a_(matrixStack, field_230690_l_ + 1, field_230691_m_ + yComponent, 0, 2, 12, 1, 16, 16); // Fuel
			}
		}
		
		GuiUtil.drawContainerBorder(matrixStack, field_230690_l_, field_230691_m_, field_230688_j_, field_230689_k_);
	}
	
	@Override
	public void func_230443_a_(MatrixStack matrixStack, int mouseX, int mouseY) {
		if (field_230692_n_) {
			final Minecraft minecraft = Minecraft.getInstance();
			final MainWindow mainWindow = minecraft.getMainWindow();
			final List<ITextComponent> list = new ArrayList<>();
			list.add(ITextComponent.func_241827_a_(storage.getAsLong() + " / " + capacity.getAsLong() + " FE"));
			GuiUtils.drawHoveringText(matrixStack, list, mouseX, mouseY, mainWindow.getWidth(), mainWindow.getHeight(), 300, minecraft.fontRenderer);
		}
	}
	
	@Override
	public void func_230988_a_(SoundHandler handler) {
		// Don't play click sound
	}
}

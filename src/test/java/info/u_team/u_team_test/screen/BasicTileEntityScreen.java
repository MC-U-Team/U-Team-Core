package info.u_team.u_team_test.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.gui.elements.*;
import info.u_team.u_team_core.screen.UBasicContainerScreen;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.BasicTileEntityContainer;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BasicTileEntityScreen extends UBasicContainerScreen<BasicTileEntityContainer> {
	
	private static final ResourceLocation BACKGROUND = new ResourceLocation(TestMod.MODID, "textures/gui/tileentity.png");
	
	private ScalableSlider slider;
	
	public BasicTileEntityScreen(BasicTileEntityContainer container, PlayerInventory playerInventory, ITextComponent text) {
		super(container, playerInventory, text, BACKGROUND, 176, 173);
	}
	
	@Override
	protected void init() {
		super.init();
		addButton(new UButton(guiLeft + xSize / 2 - 25, guiTop + 3, 50, 15, ITextComponent.getTextComponentOrEmpty("Add 100"), button -> {
			container.getValueMessage().triggerMessage();
		}));
		
		slider = addButton(new ScalableSlider(guiLeft + 7, guiTop + 19, 162, 20, ITextComponent.getTextComponentOrEmpty("Cooldown: "), ITextComponent.getTextComponentOrEmpty(" Ticks"), 0, 100, container.getTileEntity().cooldown, false, true, 1, slider -> {
			container.getCooldownMessage().triggerMessage(() -> new PacketBuffer(Unpooled.copyShort(slider.getValueInt())));
		}));
	}
	
	@Override
	public void tick() {
		super.tick();
		slider.setValue(container.getTileEntity().cooldown);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
		font.func_243248_b(matrixStack, ITextComponent.getTextComponentOrEmpty("" + container.getTileEntity().value), xSize / 2 + 32, 6, 4210752);
	}
}

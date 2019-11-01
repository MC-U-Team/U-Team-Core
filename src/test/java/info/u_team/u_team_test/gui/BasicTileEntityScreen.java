package info.u_team.u_team_test.gui;

import info.u_team.u_team_core.gui.UContainerScreen;
import info.u_team.u_team_core.gui.elements.UButton;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.BasicTileEntityContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.client.config.GuiSlider;

@OnlyIn(Dist.CLIENT)
public class BasicTileEntityScreen extends UContainerScreen<BasicTileEntityContainer> {
	
	public BasicTileEntityScreen(BasicTileEntityContainer container, PlayerInventory playerInventory, ITextComponent text) {
		super(container, playerInventory, text, new ResourceLocation(TestMod.MODID, "textures/gui/tileentity.png"));
		xSize = 176;
		ySize = 173;
	}
	
	@Override
	protected void init() {
		super.init();
		addButton(new UButton(guiLeft + xSize / 2 - 25, guiTop + 3, 50, 15, "Add 100", button -> {
			container.getTileEntity().value += 100;
		}));
		
		addButton(new GuiSlider(guiLeft + 7, guiTop + 19, 162, 20, "Cooldown: ", " Ticks", 0, 100, container.getTileEntity().cooldown, false, true, button -> {
		}, slider -> {
			container.getTileEntity().cooldown = slider.getValueInt();
		}));
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		font.drawString("" + container.getTileEntity().value, xSize / 2 + 32, 6, 4210752);
		font.drawString(title.getFormattedText(), 8, 6, 4210752);
		font.drawString(playerInventory.getDisplayName().getFormattedText(), 8.0F, ySize - 94, 4210752);
	}
}

package info.u_team.u_team_test.screen;

import info.u_team.u_team_core.gui.UContainerScreen;
import info.u_team.u_team_core.gui.elements.*;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.BasicTileEntityContainer;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class BasicTileEntityScreen extends UContainerScreen<BasicTileEntityContainer> {
	
	private BetterFontSlider slider;
	
	public BasicTileEntityScreen(BasicTileEntityContainer container, PlayerInventory playerInventory, ITextComponent text) {
		super(container, playerInventory, text, new ResourceLocation(TestMod.MODID, "textures/gui/tileentity.png"));
		xSize = 176;
		ySize = 173;
	}
	
	@Override
	protected void init() {
		super.init();
		addButton(new UButton(guiLeft + xSize / 2 - 25, guiTop + 3, 50, 15, "Add 100", button -> {
			container.getValueMessage().triggerMessage();
		}));
		
		slider = addButton(new BetterFontSlider(guiLeft + 7, guiTop + 19, 162, 20, "Cooldown: ", " Ticks", 0, 100, container.getTileEntity().cooldown, false, true, 1, slider -> {
			container.getCooldownMessage().triggerMessage(() -> new PacketBuffer(Unpooled.copyShort(slider.getValueInt())));
		}));
	}
	
	@Override
	public void tick() {
		super.tick();
		slider.setValue(container.getTileEntity().cooldown);
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
	
	@Override
	public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
		slider.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
		return super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
	}
}

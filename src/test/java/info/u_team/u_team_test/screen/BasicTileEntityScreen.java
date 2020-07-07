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
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class BasicTileEntityScreen extends UBasicContainerScreen<BasicTileEntityContainer> {
	
	private static final ResourceLocation BACKGROUND = new ResourceLocation(TestMod.MODID, "textures/gui/tileentity.png");
	
	private BetterFontSlider slider;
	
	public BasicTileEntityScreen(BasicTileEntityContainer container, PlayerInventory playerInventory, ITextComponent text) {
		super(container, playerInventory, text, BACKGROUND, 176, 173);
	}
	
	@Override
	protected void func_231160_c_() {
		super.func_231160_c_();
		func_230480_a_(new UButton(guiLeft + xSize / 2 - 25, guiTop + 3, 50, 15, ITextComponent.func_241827_a_("Add 100"), button -> {
			container.getValueMessage().triggerMessage();
		}));
		
		slider = func_230480_a_(new BetterFontSlider(guiLeft + 7, guiTop + 19, 162, 20, ITextComponent.func_241827_a_("Cooldown: "), ITextComponent.func_241827_a_(" Ticks"), 0, 100, container.getTileEntity().cooldown, false, true, 1, slider -> {
			container.getCooldownMessage().triggerMessage(() -> new PacketBuffer(Unpooled.copyShort(slider.getValueInt())));
		}));
	}
	
	@Override
	public void func_231023_e_() {
		super.func_231023_e_();
		slider.setValue(container.getTileEntity().cooldown);
	}
	
	@Override
	protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
		super.func_230451_b_(matrixStack, mouseX, mouseY);
		field_230712_o_.func_238422_b_(matrixStack, ITextComponent.func_241827_a_("" + container.getTileEntity().value), xSize / 2 + 32, 6, 4210752);
	}
	
	@Override
	public boolean func_231048_c_(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
		slider.func_231048_c_(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
		return super.func_231048_c_(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
	}
}

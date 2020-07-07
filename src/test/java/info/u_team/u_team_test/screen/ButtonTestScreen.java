package info.u_team.u_team_test.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.gui.elements.*;
import info.u_team.u_team_core.gui.render.*;
import info.u_team.u_team_test.TestMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;

public class ButtonTestScreen extends Screen {
	
	private static final ResourceLocation TEXTURE1 = new ResourceLocation(TestMod.MODID, "textures/item/better_enderpearl.png");
	private static final ResourceLocation TEXTURE2 = new ResourceLocation(TestMod.MODID, "textures/item/basicitem.png");
	
	public ButtonTestScreen() {
		super(new StringTextComponent("test"));
	}
	
	private ScalingTextRender scalingRender;
	
	private ScrollingTextRender scrollingRender;
	
	@Override
	protected void func_231160_c_() {
		func_230480_a_(new UButton(10, 10, 200, 15, ITextComponent.func_241827_a_("U Button"), button -> System.out.println("Pressed UButton")));
		
		func_230480_a_(new BetterButton(10, 30, 200, 15, 0.75F, ITextComponent.func_241827_a_("Better button"), button -> System.out.println("Pressed UButton")));
		
		final ActiveButton activeButton = func_230480_a_(new ActiveButton(10, 50, 200, 15, ITextComponent.func_241827_a_("Basic Test button"), 0x006442FF));
		activeButton.setPressable(() -> {
			System.out.println("Pressed ActiveButton");
			activeButton.setActive(!activeButton.isActive());
		});
		
		func_230480_a_(new ImageButton(10, 70, 16, 16, TEXTURE1, button -> System.out.println("Pressed ImageButton")));
		
		final ActiveImageButton activeImageButton = func_230480_a_(new ActiveImageButton(10, 90, 20, 20, TEXTURE1, 0x006442FF));
		activeImageButton.setPressable(() -> {
			System.out.println("Pressed ActiveImageButton");
			activeImageButton.setActive(!activeImageButton.isActive());
		});
		
		final ToggleImageButton toggleImageButton = func_230480_a_(new ToggleImageButton(10, 115, 20, 20, TEXTURE1, TEXTURE2));
		toggleImageButton.setPressable(() -> {
			System.out.println("Pressed ToggleImageButton");
		});
		
		func_230480_a_(new BetterFontSlider(300, 10, 200, 15, ITextComponent.func_241827_a_("Test: "), ITextComponent.func_241827_a_("%"), 0, 100, 20, false, true, 0.75F, slider -> {
			System.out.println("Updated slider value: " + slider.getValueInt() + " --> draging: " + slider.dragging);
		}));
		
		func_230480_a_(new BetterFontSlider(300, 30, 200, 40, ITextComponent.func_241827_a_("Test: "), ITextComponent.func_241827_a_("%"), 0, 100, 20, false, true, 2, slider -> {
			System.out.println("Updated slider value: " + slider.getValueInt() + " --> draging: " + slider.dragging);
		}));
		
		scalingRender = new ScalingTextRender(() -> field_230712_o_, () -> "This is a test for the scaling text renderer");
		scalingRender.setColor(0xFFFFFF);
		
		scrollingRender = new ScrollingTextRender(() -> field_230712_o_, () -> "This is a test for the scrolling text renderer that should be really long to test the scrolling");
		scrollingRender.setColor(0xFFFFFF);
		scrollingRender.setWidth(200);
	}
	
	@Override
	public void func_230430_a_(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		func_230446_a_(matrixStack);
		super.func_230430_a_(matrixStack, mouseX, mouseY, partialTicks);
		scalingRender.draw(10, 145);
		scrollingRender.draw(10, 170);
	}
	
}

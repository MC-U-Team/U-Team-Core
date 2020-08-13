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
	protected void init() {
		addButton(new UButton(10, 10, 200, 15, ITextComponent.func_244388_a("U Button"), button -> System.out.println("Pressed UButton")));
		
		addButton(new BetterButton(10, 30, 200, 15, 0.75F, ITextComponent.func_244388_a("Better button"), button -> System.out.println("Pressed UButton")));
		
		final ActiveButton activeButton = addButton(new ActiveButton(10, 50, 200, 15, ITextComponent.func_244388_a("Basic Test button"), 0x006442FF));
		activeButton.setPressable(() -> {
			System.out.println("Pressed ActiveButton");
			activeButton.setActive(!activeButton.isActive());
		});
		
		addButton(new ImageButton(10, 70, 16, 16, TEXTURE1, button -> System.out.println("Pressed ImageButton")));
		
		final ActiveImageButton activeImageButton = addButton(new ActiveImageButton(10, 90, 20, 20, TEXTURE1, 0x006442FF));
		activeImageButton.setPressable(() -> {
			System.out.println("Pressed ActiveImageButton");
			activeImageButton.setActive(!activeImageButton.isActive());
		});
		
		final ToggleImageButton toggleImageButton = addButton(new ToggleImageButton(10, 115, 20, 20, TEXTURE1, TEXTURE2));
		toggleImageButton.setPressable(() -> {
			System.out.println("Pressed ToggleImageButton");
		});
		
		addButton(new BetterFontSlider(300, 10, 200, 15, ITextComponent.func_244388_a("Test: "), ITextComponent.func_244388_a("%"), 0, 100, 20, false, true, 0.75F, slider -> {
			System.out.println("Updated slider value: " + slider.getValueInt() + " --> draging: " + slider.dragging);
		}));
		
		addButton(new BetterFontSlider(300, 30, 200, 40, ITextComponent.func_244388_a("Test: "), ITextComponent.func_244388_a("%"), 0, 100, 20, false, true, 2, slider -> {
			System.out.println("Updated slider value: " + slider.getValueInt() + " --> draging: " + slider.dragging);
		}));
		
		scalingRender = new ScalingTextRender(() -> font, () -> "This is a test for the scaling text renderer");
		scalingRender.setColor(0xFFFFFF);
		
		scrollingRender = new ScrollingTextRender(() -> font, () -> "This is a test for the scrolling text renderer that should be really long to test the scrolling");
		scrollingRender.setColor(0xFFFFFF);
		scrollingRender.setWidth(200);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		scalingRender.draw(10, 145);
		scrollingRender.draw(10, 170);
	}
	
}

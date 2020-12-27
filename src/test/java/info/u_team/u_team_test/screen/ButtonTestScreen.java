package info.u_team.u_team_test.screen;

import org.apache.logging.log4j.*;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.gui.elements.*;
import info.u_team.u_team_core.gui.renderer.*;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_test.TestMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;

public class ButtonTestScreen extends Screen {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final ResourceLocation TEXTURE1 = new ResourceLocation(TestMod.MODID, "textures/item/better_enderpearl.png");
	private static final ResourceLocation TEXTURE2 = new ResourceLocation(TestMod.MODID, "textures/item/basicitem.png");
	
	public ButtonTestScreen() {
		super(new StringTextComponent("test"));
	}
	
	private ScalingTextRenderer scalingRenderer;
	
	private ScrollingTextRenderer scrollingRenderer;
	
	@Override
	protected void init() {
		// U Button Test
		final UButton uButton = addButton(new UButton(10, 10, 200, 15, ITextComponent.getTextComponentOrEmpty("U Button")));
		uButton.setPressable(() -> LOGGER.info("Pressed U Button"));
		uButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (button.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("U Button Tooltip"), mouseX, mouseY);
			}
		});
		
		// Scalable Button Test
		final ScalableButton scalableButton = addButton(new ScalableButton(10, 30, 200, 15, ITextComponent.getTextComponentOrEmpty("Scalable Button"), 0.75F));
		scalableButton.setPressable(button -> LOGGER.info("Pressed Scalable Button"));
		scalableButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (button.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Scalable Button Tooltip"), mouseX, mouseY);
			}
		});
		
		// Scalable Active Button Test
		final ScalableActiveButton activeButton = addButton(new ScalableActiveButton(10, 50, 200, 15, ITextComponent.getTextComponentOrEmpty("Scalable Active Button"), 0.75F, false, new RGBA(0x006442FF)));
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
		
		addButton(new BetterFontSlider(300, 10, 200, 15, ITextComponent.getTextComponentOrEmpty("Test: "), ITextComponent.getTextComponentOrEmpty("%"), 0, 100, 20, false, true, 0.75F, slider -> {
			System.out.println("Updated slider value: " + slider.getValueInt() + " --> draging: " + slider.dragging);
		}));
		
		addButton(new BetterFontSlider(300, 30, 200, 40, ITextComponent.getTextComponentOrEmpty("Test: "), ITextComponent.getTextComponentOrEmpty("%"), 0, 100, 20, false, true, 2, slider -> {
			System.out.println("Updated slider value: " + slider.getValueInt() + " --> draging: " + slider.dragging);
		}));
		
		scalingRenderer = new ScalingTextRenderer(() -> font, () -> "This is a test for the scaling text renderer");
		scalingRenderer.setColor(0xFFFFFF);
		scalingRenderer.setScale(2);
		
		scrollingRenderer = new ScrollingTextRenderer(() -> font, () -> "This is a test for the scrolling text renderer that should be really long to test the scrolling");
		scrollingRenderer.setColor(0xFFFFFF);
		scrollingRenderer.setWidth(200);
		scrollingRenderer.setScale(0.75F);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		scalingRenderer.render(matrixStack, 10, 145);
		scrollingRenderer.render(matrixStack, 10, 170);
		buttons.forEach(widget -> widget.renderToolTip(matrixStack, mouseX, mouseY));
	}
	
}

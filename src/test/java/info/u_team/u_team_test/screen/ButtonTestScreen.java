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
		scalableButton.setTextColor(new RGBA(0x00FFFF80));
		scalableButton.setPressable(button -> LOGGER.info("Pressed Scalable Button"));
		scalableButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (button.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Scalable Button Tooltip"), mouseX, mouseY);
			}
		});
		
		// Scalable Activatable Button Test
		final ScalableActivatableButton scalableActivatableButton = addButton(new ScalableActivatableButton(10, 50, 200, 15, ITextComponent.getTextComponentOrEmpty("Scalable Activatable Button"), 0.75F, false, new RGBA(0x006442FF)));
		scalableActivatableButton.setPressable(() -> {
			System.out.println("Pressed Scalable Activatable Button");
			scalableActivatableButton.setActivated(!scalableActivatableButton.isActivated());
		});
		scalableActivatableButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (button.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Scalable Activatable Button Tooltip"), mouseX, mouseY);
			}
		});
		
		// Image Button Test
		final ImageButton imageButton = addButton(new ImageButton(10, 70, 15, 15, TEXTURE1));
		imageButton.setPressable(() -> {
			System.out.println("Pressed Image Button");
		});
		imageButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (button.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Image Button Tooltip"), mouseX, mouseY);
			}
		});
		
		final ImageButton imageButton2 = addButton(new ImageButton(30, 70, 15, 15, TEXTURE1));
		imageButton2.setButtonColor(new RGBA(0xFFFF2080));
		imageButton2.setImageColor(new RGBA(0x00FFFF80));
		imageButton2.setPressable(() -> {
			System.out.println("Pressed Image Button 2");
		});
		imageButton2.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (button.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Image Button 2 Tooltip"), mouseX, mouseY);
			}
		});
		
		// Image Activatable Button Test
		final ImageActivatableButton imageActivatableButton = addButton(new ImageActivatableButton(10, 90, 15, 15, TEXTURE1, false, new RGBA(0x006442FF)));
		imageActivatableButton.setPressable(() -> {
			System.out.println("Pressed Image Activatable Button");
			imageActivatableButton.setActivated(!imageActivatableButton.isActivated());
		});
		imageActivatableButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (button.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Image Activatable Button Tooltip"), mouseX, mouseY);
			}
		});
		
		// Image Toggle Button Test
		final ImageToggleButton imageToggleButton = addButton(new ImageToggleButton(10, 110, 15, 15, TEXTURE1, TEXTURE2, false));
		imageToggleButton.setPressable(() -> {
			System.out.println("Pressed Image Toggle Button");
		});
		imageToggleButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (button.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Image Toggle Button Tooltip"), mouseX, mouseY);
			}
		});
		
		final ImageToggleButton imageToggleButton2 = addButton(new ImageToggleButton(30, 110, 15, 15, TEXTURE1, TEXTURE1, false));
		imageToggleButton2.setImageColor(new RGBA(0x00FF00FF));
		imageToggleButton2.setToggleImageColor(new RGBA(0xFF0000FF));
		imageToggleButton2.setPressable(() -> {
			System.out.println("Pressed Image Toggle Button 2");
		});
		imageToggleButton2.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (button.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Image Toggle Button 2 Tooltip"), mouseX, mouseY);
			}
		});
		
		// U Slider Test
		final USlider uSlider = addButton(new USlider(10, 130, 200, 20, ITextComponent.getTextComponentOrEmpty("U Slider: "), ITextComponent.getTextComponentOrEmpty("%"), 0, 100, 20, false, true));
		uSlider.setSlider(() -> {
			System.out.println("Updated U Slider: " + uSlider.getValueInt());
		});
		uSlider.setTooltip((slider, matrixStack, mouseX, mouseY) -> {
			if (slider.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("U Slider Tooltip"), mouseX, mouseY);
			}
		});
		
		// Scalable Slider Test
		final ScalableSlider scalableSlider = addButton(new ScalableSlider(10, 155, 200, 15, ITextComponent.getTextComponentOrEmpty("Scalable Slider: "), ITextComponent.getTextComponentOrEmpty("%"), 0, 100, 20, false, true, 0.5F));
		scalableSlider.setSlider(() -> {
			System.out.println("Updated Scalable Slider: " + scalableSlider.getValueInt());
		});
		scalableSlider.setTooltip((slider, matrixStack, mouseX, mouseY) -> {
			if (slider.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Scalable Slider Tooltip"), mouseX, mouseY);
			}
		});
		
		final ScalableSlider scalableSlider2 = addButton(new ScalableSlider(10, 175, 200, 30, ITextComponent.getTextComponentOrEmpty("Scalable Slider 2: "), ITextComponent.getTextComponentOrEmpty("%"), 0, 100, 20, false, true, 1.5F));
		scalableSlider2.setSlider(() -> {
			System.out.println("Updated Scalable Slider 2: " + scalableSlider2.getValueInt());
		});
		scalableSlider2.setTooltip((slider, matrixStack, mouseX, mouseY) -> {
			if (slider.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Scalable Slider 2 Tooltip"), mouseX, mouseY);
			}
		});
		
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
		// scalingRenderer.render(matrixStack, 10, 145);
		// scrollingRenderer.render(matrixStack, 10, 170);
		buttons.forEach(widget -> widget.renderToolTip(matrixStack, mouseX, mouseY));
	}
	
}

package info.u_team.u_team_test.screen;

import org.apache.logging.log4j.*;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.gui.elements.*;
import info.u_team.u_team_core.gui.renderer.*;
import info.u_team.u_team_core.util.*;
import info.u_team.u_team_test.TestMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;

public class ButtonTestScreen extends Screen {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final ResourceLocation TEXTURE1 = new ResourceLocation(TestMod.MODID, "textures/item/better_enderpearl.png");
	private static final ResourceLocation TEXTURE2 = new ResourceLocation(TestMod.MODID, "textures/item/basicitem.png");
	
	private TextFieldWidget textFieldWidget;
	
	private ScalingTextRenderer scalingRenderer;
	
	private ScrollingTextRenderer scrollingRenderer;
	
	public ButtonTestScreen() {
		super(new StringTextComponent("test"));
	}
	
	@Override
	protected void init() {
		// U Button Test
		final UButton uButton = addButton(new UButton(10, 10, 200, 15, ITextComponent.getTextComponentOrEmpty("U Button")));
		uButton.setPressable(() -> LOGGER.info("Pressed U Button"));
		uButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("U Button Tooltip"), mouseX, mouseY);
			}
		});
		
		// Scalable Button Test
		final ScalableButton scalableButton = addButton(new ScalableButton(10, 30, 200, 15, ITextComponent.getTextComponentOrEmpty("Scalable Button"), 0.75F));
		scalableButton.setTextColor(new RGBA(0x00FFFF80));
		scalableButton.setPressable(button -> LOGGER.info("Pressed Scalable Button"));
		scalableButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Scalable Button Tooltip"), mouseX, mouseY);
			}
		});
		
		// Scalable Activatable Button Test
		final ScalableActivatableButton scalableActivatableButton = addButton(new ScalableActivatableButton(10, 50, 200, 15, ITextComponent.getTextComponentOrEmpty("Scalable Activatable Button"), 0.75F, false, new RGBA(0x006442FF)));
		scalableActivatableButton.setPressable(() -> {
			LOGGER.info("Pressed Scalable Activatable Button");
			scalableActivatableButton.setActivated(!scalableActivatableButton.isActivated());
		});
		scalableActivatableButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Scalable Activatable Button Tooltip"), mouseX, mouseY);
			}
		});
		
		// Image Button Test
		final ImageButton imageButton = addButton(new ImageButton(10, 70, 15, 15, TEXTURE1));
		imageButton.setPressable(() -> {
			LOGGER.info("Pressed Image Button");
		});
		imageButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Image Button Tooltip"), mouseX, mouseY);
			}
		});
		
		final ImageButton imageButton2 = addButton(new ImageButton(30, 70, 15, 15, TEXTURE1));
		imageButton2.setButtonColor(new RGBA(0xFFFF2080));
		imageButton2.setImageColor(new RGBA(0x00FFFF80));
		imageButton2.setPressable(() -> {
			LOGGER.info("Pressed Image Button 2");
		});
		imageButton2.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Image Button 2 Tooltip"), mouseX, mouseY);
			}
		});
		
		// Image Activatable Button Test
		final ImageActivatableButton imageActivatableButton = addButton(new ImageActivatableButton(10, 90, 15, 15, TEXTURE1, false, new RGBA(0x006442FF)));
		imageActivatableButton.setPressable(() -> {
			LOGGER.info("Pressed Image Activatable Button");
			imageActivatableButton.setActivated(!imageActivatableButton.isActivated());
		});
		imageActivatableButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Image Activatable Button Tooltip"), mouseX, mouseY);
			}
		});
		
		// Image Toggle Button Test
		final ImageToggleButton imageToggleButton = addButton(new ImageToggleButton(10, 110, 15, 15, TEXTURE1, TEXTURE2, false));
		imageToggleButton.setPressable(() -> {
			LOGGER.info("Pressed Image Toggle Button");
		});
		imageToggleButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Image Toggle Button Tooltip"), mouseX, mouseY);
			}
		});
		
		final ImageToggleButton imageToggleButton2 = addButton(new ImageToggleButton(30, 110, 15, 15, TEXTURE1, TEXTURE1, false));
		imageToggleButton2.setImageColor(new RGBA(0x00FF00FF));
		imageToggleButton2.setToggleImageColor(new RGBA(0xFF0000FF));
		imageToggleButton2.setPressable(() -> {
			LOGGER.info("Pressed Image Toggle Button 2");
		});
		imageToggleButton2.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Image Toggle Button 2 Tooltip"), mouseX, mouseY);
			}
		});
		
		// U Slider Test
		final USlider uSlider = addButton(new USlider(10, 130, 200, 20, ITextComponent.getTextComponentOrEmpty("U Slider: "), ITextComponent.getTextComponentOrEmpty("%"), 0, 100, 20, false, true, false));
		uSlider.setSlider(() -> {
			LOGGER.info("Updated U Slider: " + uSlider.getValueInt());
		});
		uSlider.setTooltip((slider, matrixStack, mouseX, mouseY) -> {
			if (slider.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("U Slider Tooltip"), mouseX, mouseY);
			}
		});
		
		// Scalable Slider Test
		final ScalableSlider scalableSlider = addButton(new ScalableSlider(10, 155, 200, 15, ITextComponent.getTextComponentOrEmpty("Scalable Slider: "), ITextComponent.getTextComponentOrEmpty("%"), 0, 100, 20, false, true, false, 0.5F));
		scalableSlider.setSlider(() -> {
			LOGGER.info("Updated Scalable Slider: " + scalableSlider.getValueInt());
		});
		scalableSlider.setTooltip((slider, matrixStack, mouseX, mouseY) -> {
			if (slider.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Scalable Slider Tooltip"), mouseX, mouseY);
			}
		});
		
		final ScalableSlider scalableSlider2 = addButton(new ScalableSlider(10, 175, 200, 30, ITextComponent.getTextComponentOrEmpty("Scalable Slider 2: "), ITextComponent.getTextComponentOrEmpty("%"), 0, 100, 20, false, true, false, 1.5F));
		scalableSlider2.setSliderBackgroundColor(new RGBA(0x0000FFFF));
		scalableSlider2.setSliderColor(new RGBA(0x00FF00FF));
		scalableSlider2.setTextColor(new RGBA(0xFF0000FF));
		scalableSlider2.setDisabledTextColor(new RGBA(0xFFFF0080));
		scalableSlider2.setSlider(() -> {
			LOGGER.info("Updated Scalable Slider 2: " + scalableSlider2.getValueInt());
			if (scalableSlider2.getValueInt() == 100) {
				scalableSlider2.active = false;
			}
		});
		scalableSlider2.setTooltip((slider, matrixStack, mouseX, mouseY) -> {
			if (slider.isHovered()) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Scalable Slider 2 Tooltip"), mouseX, mouseY);
				scalableSlider2.active = true;
			}
		});
		
		// Checkbox Button Test
		final CheckboxButton checkboxButton = addButton(new CheckboxButton(10, 210, 15, 15, ITextComponent.getTextComponentOrEmpty("Checkbox Button"), false, true));
		checkboxButton.setPressable(() -> {
			LOGGER.info("Pressed Checkbox Button");
		});
		checkboxButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Checkbox Button Tooltip"), mouseX, mouseY);
			}
		});
		
		final CheckboxButton checkboxButton2 = addButton(new CheckboxButton(110, 230, 15, 15, ITextComponent.getTextComponentOrEmpty("Checkbox Button 2"), false, true));
		checkboxButton2.setLeftSideText(true);
		checkboxButton2.setPressable(() -> {
			LOGGER.info("Pressed Checkbox Button 2");
		});
		checkboxButton2.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Checkbox Button 2 Tooltip"), mouseX, mouseY);
			}
		});
		
		// Scalable Checkbox Button Test
		final ScalableCheckboxButton scalableCheckboxButton = addButton(new ScalableCheckboxButton(10, 250, 15, 15, ITextComponent.getTextComponentOrEmpty("Scalable Checkbox Button"), false, true, 0.75F));
		scalableCheckboxButton.setPressable(() -> {
			LOGGER.info("Pressed Scalable Checkbox Button");
		});
		scalableCheckboxButton.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Scalable Checkbox Button Tooltip"), mouseX, mouseY);
			}
		});
		
		final ScalableCheckboxButton scalableCheckboxButton2 = addButton(new ScalableCheckboxButton(110, 270, 15, 15, ITextComponent.getTextComponentOrEmpty("Scalable Checkbox Button 2"), false, true, 0.65F));
		scalableCheckboxButton2.setLeftSideText(true);
		scalableCheckboxButton2.setButtonColor(new RGBA(0x0000F0FF));
		scalableCheckboxButton2.setTextColor(new RGBA(0xA0A0A0FF));
		scalableCheckboxButton2.setPressable(() -> {
			LOGGER.info("Pressed Scalable Checkbox Button 2");
		});
		scalableCheckboxButton2.setTooltip((button, matrixStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(matrixStack, ITextComponent.getTextComponentOrEmpty("Scalable Checkbox Button 2 Tooltip"), mouseX, mouseY);
			}
		});
		
		// TEST TEST
		this.minecraft.keyboardListener.enableRepeatEvents(true);
		textFieldWidget = addButton(new TextFieldWidget(font, 10, 290, 200, 20, null, new TranslationTextComponent("mco.configure.world.invite.profile.name")));
		textFieldWidget.setCanLoseFocus(false);
		setFocusedDefault(textFieldWidget);
		
		scalingRenderer = new ScalingTextRenderer(() -> font, () -> "This is a test for the scaling text renderer", 220, 10);
		scalingRenderer.setColor(new RGBA(0xFF00FF40));
		scalingRenderer.setScale(1.5F);
		
		scrollingRenderer = new ScrollingTextRenderer(() -> font, () -> "This is a test for the scrolling text renderer that should be really long to test the scrolling", 220, 25);
		scrollingRenderer.setColor(new RGBA(0x00FFFFFF));
		scrollingRenderer.setWidth(200);
		scrollingRenderer.setScale(2F);
	}
	
	@Override
	public void onClose() {
		this.minecraft.keyboardListener.enableRepeatEvents(false);
	}
	
	@Override
	public void tick() {
		textFieldWidget.tick();
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		scalingRenderer.render(matrixStack, mouseX, mouseY, partialTicks);
		scrollingRenderer.render(matrixStack, mouseX, mouseY, partialTicks);
		buttons.forEach(widget -> widget.renderToolTip(matrixStack, mouseX, mouseY));
	}
	
}

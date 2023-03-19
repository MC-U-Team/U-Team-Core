package info.u_team.u_team_test.screen;

import org.slf4j.Logger;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.TextAndImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class ButtonTestScreenVanilla extends Screen {
	
	private static final Logger LOGGER = LogUtils.getLogger();
	
	private int sliderValue = 0;
	
	public ButtonTestScreenVanilla() {
		super(Component.literal("test"));
	}
	
	@Override
	protected void init() {
		addRenderableWidget(new Button.Builder(Component.literal("Test Button"), button -> {
			LOGGER.info("Clicked Testbutton");
		}).bounds(50, 50, 200, 20).tooltip(Tooltip.create(Component.literal("Tooltip for Test Button"))).build());
		
		addRenderableWidget(new ImageButton(50, 80, 50, 20, 0, 106, 20, Button.WIDGETS_LOCATION, 256, 256, button -> {
			LOGGER.info("Clicked Test ImageButton");
		}, Component.literal("Test ImageButton")));
		
		final AbstractWidget textImageButton = addRenderableWidget(TextAndImageButton.builder(Component.literal("Test Button with Image"), Button.WIDGETS_LOCATION, button -> {
			LOGGER.info("Clicked Testbutton with Image");
		}).texStart(3, 109).offset(65, 3).yDiffTex(20).usedTextureSize(14, 14).textureSize(256, 256).build());
		textImageButton.setPosition(50, 110);
		textImageButton.setTooltip(Tooltip.create(Component.literal("Tooltip for Test Button with Image")));
		
		final AbstractWidget editBox = addRenderableWidget(new EditBox(font, 50, 140, 200, 20, Component.literal("Test box")));
		editBox.setTooltip(Tooltip.create(Component.literal("Test text box")));
		
		final AbstractWidget multiEditBox = addRenderableWidget(new MultiLineEditBox(font, 50, 170, 200, 20, Component.empty(), Component.literal("Test box")));
		multiEditBox.setTooltip(Tooltip.create(Component.literal("Test multi line text box")));
		
		addRenderableWidget(new AbstractSliderButton(50, 200, 200, 20, Component.empty(), 0) {
			
			{
				updateMessage();
			}
			
			@Override
			protected void updateMessage() {
				setMessage(Component.literal("Slider Test " + sliderValue));
			}
			
			@Override
			protected void applyValue() {
				sliderValue = Mth.floor(Mth.clampedLerp(0, 50, value));
				LOGGER.info("Update slider value to {}", sliderValue);
			}
		});
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, partialTick);
	}
	
}

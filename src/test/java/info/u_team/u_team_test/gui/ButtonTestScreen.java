package info.u_team.u_team_test.gui;

import info.u_team.u_team_core.gui.elements.*;
import info.u_team.u_team_test.TestMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class ButtonTestScreen extends Screen {
	
	private static final ResourceLocation TEXTURE1 = new ResourceLocation(TestMod.MODID, "textures/item/better_enderpearl.png");
	private static final ResourceLocation TEXTURE2 = new ResourceLocation(TestMod.MODID, "textures/item/basicitem.png");
	
	public ButtonTestScreen() {
		super(new StringTextComponent("test"));
	}
	
	@Override
	protected void init() {
		addButton(new UButton(10, 10, 200, 15, "U Button", button -> System.out.println("Pressed UButton")));
		ActiveButton activeButton = addButton(new ActiveButton(10, 30, 200, 15, "Basic Test button", 0x006442FF));
		activeButton.setPressable(() -> {
			System.out.println("Pressed ActiveButton");
			activeButton.setActive(!activeButton.isActive());
		});
		
		addButton(new ImageButton(10, 50, 16, 16, TEXTURE1, button -> System.out.println("Pressed ImageButton")));
		
		ActiveImageButton activeImageButton = addButton(new ActiveImageButton(10, 75, 20, 20, TEXTURE1, 0x006442FF));
		activeImageButton.setPressable(() -> {
			System.out.println("Pressed ActiveImageButton");
			activeImageButton.setActive(!activeImageButton.isActive());
		});
		
		ToggleImageButton toggleImageButton = addButton(new ToggleImageButton(10, 110, 20, 20, TEXTURE1, TEXTURE2));
		toggleImageButton.setPressable(() -> {
			System.out.println("Pressed ToggleImageButton");
		});
		
		addButton(new BetterFontSlider(300, 10, 200, 15, "Test: ", "%", 0, 100, 20, false, true, 0.8F, slider -> {
			System.out.println("Updated slider value: " + slider.getValueInt() + " --> draging: " + slider.dragging);
		}));
	}
	
	@Override
	public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
		renderBackground();
		super.render(p_render_1_, p_render_2_, p_render_3_);
	}
	
}

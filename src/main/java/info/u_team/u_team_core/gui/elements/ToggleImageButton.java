package info.u_team.u_team_core.gui.elements;

import net.minecraft.util.ResourceLocation;

public class ToggleImageButton extends ImageButton {
	
	protected ResourceLocation defaultResource;
	protected ResourceLocation toggleResource;
	
	protected boolean toggled;
	
	public ToggleImageButton(int x, int y, int width, int height, ResourceLocation defaultResource, ResourceLocation toggleResource) {
		this(x, y, width, height, defaultResource, toggleResource, EMTPY_PRESSABLE);
	}
	
	public ToggleImageButton(int x, int y, int width, int height, ResourceLocation defaultResource, ResourceLocation toggleResource, IPressable pressable) {
		super(x, y, width, height, defaultResource, pressable);
		this.defaultResource = defaultResource;
		this.toggleResource = toggleResource;
	}
	
	public ResourceLocation getDefaultResource() {
		return defaultResource;
	}
	
	public void setDefaultResource(ResourceLocation defaultResource) {
		this.defaultResource = defaultResource;
	}
	
	public ResourceLocation getToggleResource() {
		return toggleResource;
	}
	
	public void setToggleResource(ResourceLocation toggleResource) {
		this.toggleResource = toggleResource;
	}
	
	public boolean isToggled() {
		return toggled;
	}
	
	public void toggle() {
		toggle(!toggled);
	}
	
	public void toggle(boolean value) {
		toggled = value;
		setResource(toggled ? toggleResource : defaultResource);
	}
	
	@Override
	public void onPress() {
		toggle();
		super.onPress();
	}
}
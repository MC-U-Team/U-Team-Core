package info.u_team.u_team_core.gui.elements;

import java.util.function.IntSupplier;

import info.u_team.u_team_core.api.gui.TextureProvider;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.ResourceLocation;

public class WidgetTextureProvider implements TextureProvider {
	
	protected AbstractWidget widget;
	protected IntSupplier yImage;
	
	public WidgetTextureProvider(AbstractWidget widget, IntSupplier yImage) {
		this.widget = widget;
		this.yImage = yImage;
	}
	
	@Override
	public ResourceLocation getTexture() {
		return AbstractWidget.WIDGETS_LOCATION;
	}
	
	@Override
	public int getU() {
		return 0;
	}
	
	@Override
	public int getV() {
		return 46 + yImage.getAsInt() * 20;
	}
	
	@Override
	public int getWidth() {
		return 200;
	}
	
	@Override
	public int getHeight() {
		return 20;
	}
	
}

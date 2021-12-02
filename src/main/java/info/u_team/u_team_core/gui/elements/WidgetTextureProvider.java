package info.u_team.u_team_core.gui.elements;

import java.util.function.Function;

import info.u_team.u_team_core.api.gui.TextureProvider;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.ResourceLocation;

public class WidgetTextureProvider implements TextureProvider {
	
	protected AbstractWidget widget;
	protected Function<Boolean, Integer> yImage;
	
	public WidgetTextureProvider(AbstractWidget widget, Function<Boolean, Integer> yImage) {
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
		return 46 + yImage.apply(widget.isHoveredOrFocused()) * 20;
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

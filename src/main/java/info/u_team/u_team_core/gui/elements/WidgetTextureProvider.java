package info.u_team.u_team_core.gui.elements;

import java.util.function.Function;

import info.u_team.u_team_core.api.gui.ITextureProvider;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;

public class WidgetTextureProvider implements ITextureProvider {
	
	protected Widget widget;
	protected Function<Boolean, Integer> yImage;
	
	public WidgetTextureProvider(Widget widget, Function<Boolean, Integer> yImage) {
		this.widget = widget;
		this.yImage = yImage;
	}
	
	@Override
	public ResourceLocation getTexture() {
		return Widget.WIDGETS_LOCATION;
	}
	
	@Override
	public int getU() {
		return 0;
	}
	
	@Override
	public int getV() {
		return 46 + yImage.apply(widget.isHovered()) * 20;
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

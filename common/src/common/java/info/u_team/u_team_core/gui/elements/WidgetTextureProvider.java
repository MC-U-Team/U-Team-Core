package info.u_team.u_team_core.gui.elements;

import java.util.function.IntSupplier;

import info.u_team.u_team_core.api.gui.TextureProvider;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.ResourceLocation;

public class WidgetTextureProvider implements TextureProvider {
	
	protected final ResourceLocation texture;
	protected final IntSupplier vSupplier;
	
	public WidgetTextureProvider(IntSupplier vSupplier) {
		this(AbstractWidget.WIDGETS_LOCATION, vSupplier);
	}
	
	public WidgetTextureProvider(ResourceLocation texture, IntSupplier vSupplier) {
		this.texture = texture;
		this.vSupplier = vSupplier;
	}
	
	@Override
	public ResourceLocation getTexture() {
		return texture;
	}
	
	@Override
	public int getU() {
		return 0;
	}
	
	@Override
	public int getV() {
		return vSupplier.getAsInt();
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

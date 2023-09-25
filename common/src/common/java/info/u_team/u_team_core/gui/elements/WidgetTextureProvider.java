package info.u_team.u_team_core.gui.elements;

import java.util.function.BooleanSupplier;

import info.u_team.u_team_core.api.gui.TextureProvider;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class WidgetTextureProvider implements TextureProvider {
	
	protected final WidgetSprites sprites;
	protected final BooleanSupplier enabled, focused;
	
	public WidgetTextureProvider(WidgetSprites sprites, BooleanSupplier enabled, BooleanSupplier focused) {
		this.sprites = sprites;
		this.enabled = enabled;
		this.focused = focused;
	}
	
	@Override
	public ResourceLocation getTexture() {
		return sprites.get(enabled.getAsBoolean(), focused.getAsBoolean());
	}
	
}

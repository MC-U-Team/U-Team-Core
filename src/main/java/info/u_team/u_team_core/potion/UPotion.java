package info.u_team.u_team_core.potion;

import info.u_team.u_team_core.api.registry.IUPotion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.potion.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.*;

public class UPotion extends Potion implements IUPotion {
	
	protected final String name;
	
	protected final int texturesize;
	
	protected String texturedomain;
	protected final String texturename;
	
	@OnlyIn(Dist.CLIENT)
	private ResourceLocation texture;
	
	public UPotion(String name, boolean isBadEffect, int liquidColor) {
		this(name, isBadEffect, liquidColor, (String) null, 0);
	}
	
	public UPotion(String name, boolean isBadEffect, int liquidColor, int texturesize) {
		this(name, isBadEffect, liquidColor, "textures/potion/" + name + ".png", texturesize);
	}
	
	public UPotion(String name, boolean isBadEffect, int liquidColor, String texturename, int texturesize) {
		this(name, isBadEffect, liquidColor, null, texturename, texturesize);
	}
	
	public UPotion(String name, boolean isBadEffect, int liquidColor, ResourceLocation textureresource, int texturesize) {
		this(name, isBadEffect, liquidColor, textureresource.getNamespace(), textureresource.getPath(), texturesize);
	}
	
	public UPotion(String name, boolean isBadEffect, int liquidColor, String texturedomain, String texturename, int texturesize) {
		super(isBadEffect, liquidColor);
		this.name = name;
		this.texturedomain = texturedomain;
		this.texturename = texturename;
		this.texturesize = texturesize;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	@OnlyIn(Dist.CLIENT)
	public void bindTexture(TextureManager manager) {
		if (texturename == null) {
			return;
		}
		if (texture == null) {
			texture = new ResourceLocation(texturedomain == null ? getRegistryName().getNamespace() : texturedomain, texturename);
		}
		manager.bindTexture(texture);
	}
	
	@Override
	public boolean shouldRender(PotionEffect effect) {
		return texturename != null;
	}
	
	@Override
	public boolean shouldRenderHUD(PotionEffect effect) {
		return texturename != null;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderInventoryEffect(PotionEffect effect, Gui gui, int x, int y, float z) {
		bindTexture(Minecraft.getInstance().getTextureManager());
		Gui.drawScaledCustomSizeModalRect(x + 6, y + 7, 0, 0, texturesize, texturesize, 18, 18, texturesize, texturesize);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderHUDEffect(PotionEffect effect, net.minecraft.client.gui.Gui gui, int x, int y, float z, float alpha) {
		bindTexture(Minecraft.getInstance().getTextureManager());
		Gui.drawScaledCustomSizeModalRect(x + 3, y + 3, 0, 0, texturesize, texturesize, 18, 18, texturesize, texturesize);
	}
	
}

package info.u_team.u_team_core.api.gui;

import net.minecraft.resources.ResourceLocation;

public interface TextureProvider {
	
	ResourceLocation getTexture();
	
	int getU();
	
	int getV();
	
	int getWidth();
	
	int getHeight();
	
}

package info.u_team.u_team_core.api.gui;

import net.minecraft.util.ResourceLocation;

public interface ITextureProvider {
	
	ResourceLocation getTexture();
	
	int getU();
	
	int getV();
	
	int getWidth();
	
	int getHeight();
	
}

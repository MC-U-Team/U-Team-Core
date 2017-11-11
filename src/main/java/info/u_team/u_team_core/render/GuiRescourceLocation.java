package info.u_team.u_team_core.render;

import info.u_team.u_team_core.sub.USub;
import net.minecraft.util.ResourceLocation;

/**
 * Custom Resource for Gui Textures
 * 
 * @author MrTroble
 * @date 21.10.2017
 *
 */
public class GuiRescourceLocation extends ResourceLocation {
	
	public GuiRescourceLocation(String texture_name) {
		super(USub.getID(), "textures/gui/" + texture_name);
	}
	
}

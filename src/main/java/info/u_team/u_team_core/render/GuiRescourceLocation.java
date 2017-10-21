package info.u_team.u_team_core.render;

import info.u_team.u_team_core.sub.USub;
import net.minecraft.util.ResourceLocation;

public class GuiRescourceLocation extends ResourceLocation {

	public GuiRescourceLocation(String texture_name) {
		super(USub.getID(), "textures/gui/" + texture_name);
	}

}

package info.u_team.u_team_core.api.gui;

import net.minecraft.network.chat.Component;

public interface TextProvider extends TextSettingsProvider {
	
	Component getCurrentText();
	
}

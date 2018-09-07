package info.u_team.u_team_core.intern.event;

import info.u_team.u_team_core.UCoreConstants;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class EventHandlerConfigChange {
	
	@SubscribeEvent
	public void onConfigChangedEvent(OnConfigChangedEvent event) {
		if (event.getModID().equals(UCoreConstants.MODID)) {
			ConfigManager.sync(UCoreConstants.MODID, Type.INSTANCE);
		}
	}
}
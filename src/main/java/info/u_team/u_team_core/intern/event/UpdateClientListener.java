package info.u_team.u_team_core.intern.event;

import info.u_team.u_team_core.updatechecker.UpdateCheckerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;

/**
 * Internal Event for update checker
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 *
 */
@SideOnly(Side.CLIENT)
public class UpdateClientListener {
	
	private boolean show = false;
	
	private Minecraft minecraft = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void on(RenderGameOverlayEvent event) {
		if (!show) {
			if (!minecraft.inGameHasFocus || minecraft.player == null) {
				return;
			}
			show = true;
			UpdateCheckerRegistry.getChecker().createChat().forEach(component -> minecraft.player.sendMessage(component));
		}
	}
}

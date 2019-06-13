package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.intern.command.CommandUTeamCore;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@EventBusSubscriber(modid = UCoreMain.modid)
public class UCoreCommands {
	
	@SubscribeEvent
	public static void on(FMLServerStartingEvent event) {
		new CommandUTeamCore(event.getCommandDispatcher());
	}
	
}

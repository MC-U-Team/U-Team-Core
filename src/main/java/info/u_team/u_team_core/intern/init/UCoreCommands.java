package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.intern.command.UTeamCoreCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = UCoreMain.MODID, bus = Bus.FORGE)
public class UCoreCommands {
	
	@SubscribeEvent
	public static void on(RegisterCommandsEvent event) {
		new UTeamCoreCommand(event.getDispatcher());
	}
	
}

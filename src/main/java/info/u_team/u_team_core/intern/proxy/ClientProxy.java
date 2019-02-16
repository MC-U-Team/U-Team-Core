package info.u_team.u_team_core.intern.proxy;

import info.u_team.u_team_core.intern.discord.DiscordRichPresence;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class ClientProxy {
	
	public static void setup() {
		DiscordRichPresence.start();
	}
	
}

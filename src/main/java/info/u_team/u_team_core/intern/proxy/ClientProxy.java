package info.u_team.u_team_core.intern.proxy;

import info.u_team.u_team_core.intern.config.ClientConfig;
import info.u_team.u_team_core.intern.discord.DiscordRichPresence;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {
	
	@Override
	public void construct() {
		super.construct();
	}
	
	@Override
	public void setup() {
		super.setup();
		if (ClientConfig.getInstance().discordRichPresence.get()) {
			DiscordRichPresence.start();
		}
	}
	
	@Override
	public void complete() {
		super.complete();
	}
	
}

package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.intern.network.SyncedContainerMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class UCoreNetwork {
	
	public static final String protocol = "1.14.2-1";
	
	public static final SimpleChannel network = NetworkRegistry.newSimpleChannel(new ResourceLocation(UCoreMain.modid, "network"), () -> protocol, protocol::equals, protocol::equals);
	
	public static void construct() {
		network.registerMessage(0, SyncedContainerMessage.class, SyncedContainerMessage::encode, SyncedContainerMessage::decode, SyncedContainerMessage.Handler::handle);
	}
	
}

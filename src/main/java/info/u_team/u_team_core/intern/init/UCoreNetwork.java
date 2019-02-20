package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.intern.network.MessageSyncedContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class UCoreNetwork {
	
	public static final String protocol = "1";
	
	public static final SimpleChannel network = NetworkRegistry.newSimpleChannel(new ResourceLocation(UCoreMain.modid, "network"), () -> protocol, protocol::equals, protocol::equals);
	
	public static void construct() {
		network.registerMessage(1, MessageSyncedContainer.class, MessageSyncedContainer::encode, MessageSyncedContainer::decode, MessageSyncedContainer.Handler::handle);
	}
	
}

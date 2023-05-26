package info.u_team.u_team_core.intern.init;

import java.util.Optional;

import info.u_team.u_team_core.UCoreReference;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.intern.init.network.DataHolderMenuMessage;
import info.u_team.u_team_core.intern.init.network.OpenMenuScreenMessage;
import net.minecraft.resources.ResourceLocation;

public class UCoreNetwork {
	
	public static final NetworkHandler NETWORK = NetworkHandler.create("1.19.4-2", new ResourceLocation(UCoreReference.MODID, "network"));
	
	static void register() {
		NETWORK.registerMessage(0, DataHolderMenuMessage.class, DataHolderMenuMessage::encode, DataHolderMenuMessage::decode, DataHolderMenuMessage.Handler::handle);
		NETWORK.registerMessage(1, OpenMenuScreenMessage.class, OpenMenuScreenMessage::encode, OpenMenuScreenMessage::decode, OpenMenuScreenMessage.Handler::handle, Optional.of(NetworkEnvironment.CLIENT));
	}
	
}

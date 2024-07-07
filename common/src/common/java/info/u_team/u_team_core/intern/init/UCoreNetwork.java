package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreReference;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkHandlerEnvironment;
import info.u_team.u_team_core.api.network.NetworkMessage;
import info.u_team.u_team_core.intern.network.DataHolderMenuMessage;
import info.u_team.u_team_core.intern.network.OpenMenuScreenMessage;
import net.minecraft.resources.ResourceLocation;

public class UCoreNetwork {
	
	private static final NetworkHandler NETWORK = NetworkHandler.create(ResourceLocation.fromNamespaceAndPath(UCoreReference.MODID, "network"), UCoreReference.PROTOCOL_VERSION);
	
	public static final NetworkMessage<DataHolderMenuMessage> DATA_HOLDER_MENU_MESSAGE = NETWORK.register("data_holder_menu", NetworkHandlerEnvironment.BOTH, DataHolderMenuMessage.STREAM_CODEC, DataHolderMenuMessage::handle);
	public static final NetworkMessage<OpenMenuScreenMessage> OPEN_MENU_SCREEN_MESSAGE = NETWORK.register("open_menu", NetworkHandlerEnvironment.CLIENT, OpenMenuScreenMessage.STREAM_CODEC, OpenMenuScreenMessage::handle);
	
	static void register() {
		NETWORK.register();
	}
	
}

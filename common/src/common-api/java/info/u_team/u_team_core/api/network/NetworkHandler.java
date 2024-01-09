package info.u_team.u_team_core.api.network;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.resources.ResourceLocation;

public interface NetworkHandler {
	
	static NetworkHandler create(ResourceLocation channel, int protocolVersion) {
		return Factory.INSTANCE.create(channel, protocolVersion);
	}
	
	<M> NetworkMessage<M> register(int index, NetworkPayload<M> payload);
	
	void register();
	
	ResourceLocation getChannel();
	
	int getProtocolVersion();
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
		
		NetworkHandler create(ResourceLocation channel, int protocolVersion);
	}
}

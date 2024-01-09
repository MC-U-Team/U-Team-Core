package info.u_team.u_team_core.impl.common;

import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkPayload;
import net.minecraft.resources.ResourceLocation;

public abstract class CommonNetworkHandler implements NetworkHandler {
	
	protected final ResourceLocation channel;
	protected final int protocolVersion;
	
	protected CommonNetworkHandler(ResourceLocation channel, int protocolVersion) {
		this.channel = channel;
		this.protocolVersion = protocolVersion;
	}
	
	protected <M> void validateNetworkPayload(ResourceLocation messageId, NetworkPayload<M> payload) {
		if (payload.getHandlerEnvironment().isEmpty()) {
			throw new IllegalArgumentException("Handler environment cannot be empty for message id " + messageId);
		}
	}
	
	@Override
	public ResourceLocation getChannel() {
		return channel;
	}
	
	@Override
	public int getProtocolVersion() {
		return protocolVersion;
	}
	
}

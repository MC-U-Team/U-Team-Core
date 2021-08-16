package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.intern.network.BufferPropertyContainerMessage;
import info.u_team.u_team_core.intern.network.FluidClickContainerMessage;
import info.u_team.u_team_core.intern.network.FluidSetAllContainerMessage;
import info.u_team.u_team_core.intern.network.FluidSetSlotContainerMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class UCoreNetwork {
	
	public static final String PROTOCOL = "1.16.5-1";
	
	public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation(UCoreMod.MODID, "network"), () -> PROTOCOL, version -> {
		// Allow clients to join / view servers without errors when uteamcore is not present there
		if (version.equals(NetworkRegistry.ABSENT) || version.equals(NetworkRegistry.ACCEPTVANILLA)) {
			return true;
		}
		return PROTOCOL.equals(version);
	}, PROTOCOL::equals);
	
	public static void setup(FMLCommonSetupEvent event) {
		NETWORK.registerMessage(0, BufferPropertyContainerMessage.class, BufferPropertyContainerMessage::encode, BufferPropertyContainerMessage::decode, BufferPropertyContainerMessage.Handler::handle);
		NETWORK.registerMessage(1, FluidSetAllContainerMessage.class, FluidSetAllContainerMessage::encode, FluidSetAllContainerMessage::decode, FluidSetAllContainerMessage.Handler::handle);
		NETWORK.registerMessage(2, FluidSetSlotContainerMessage.class, FluidSetSlotContainerMessage::encode, FluidSetSlotContainerMessage::decode, FluidSetSlotContainerMessage.Handler::handle);
		NETWORK.registerMessage(3, FluidClickContainerMessage.class, FluidClickContainerMessage::encode, FluidClickContainerMessage::decode, FluidClickContainerMessage.Handler::handle);
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UCoreNetwork::setup);
	}
	
}

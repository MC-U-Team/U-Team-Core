package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.UCoreReference;
import info.u_team.u_team_core.intern.network.ContainerSetFluidContentMessage;
import info.u_team.u_team_core.intern.network.ContainerSetFluidSlotMessage;
import info.u_team.u_team_core.intern.network.FluidClickContainerMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.Channel.VersionTest;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;

public class UCoreForgeNetwork {
	
	public static final int PROTOCOL = UCoreReference.PROTOCOL_VERSION;
	
	public static final SimpleChannel NETWORK = ChannelBuilder.named(new ResourceLocation(UCoreMod.MODID, "network_forge")) //
			.networkProtocolVersion(PROTOCOL) //
			.optionalServer() //
			.serverAcceptedVersions(VersionTest.exact(PROTOCOL)) //
			.simpleChannel();
	
	public static void setup(FMLCommonSetupEvent event) {
		NETWORK.messageBuilder(ContainerSetFluidContentMessage.class, 0x1, NetworkDirection.PLAY_TO_CLIENT).encoder(ContainerSetFluidContentMessage::encode).decoder(ContainerSetFluidContentMessage::decode).consumerNetworkThread(ContainerSetFluidContentMessage.Handler::handle).add();
		NETWORK.messageBuilder(ContainerSetFluidSlotMessage.class, 0x2, NetworkDirection.PLAY_TO_CLIENT).encoder(ContainerSetFluidSlotMessage::encode).decoder(ContainerSetFluidSlotMessage::decode).consumerNetworkThread(ContainerSetFluidSlotMessage.Handler::handle).add();
		NETWORK.messageBuilder(FluidClickContainerMessage.class, 0x3, NetworkDirection.PLAY_TO_SERVER).encoder(FluidClickContainerMessage::encode).decoder(FluidClickContainerMessage::decode).consumerNetworkThread(FluidClickContainerMessage.Handler::handle).add();
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UCoreForgeNetwork::setup);
	}
	
}

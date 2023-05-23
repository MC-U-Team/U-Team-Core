package info.u_team.u_team_core.intern.init;

import java.util.Optional;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.intern.network.ContainerSetFluidContentMessage;
import info.u_team.u_team_core.intern.network.ContainerSetFluidSlotMessage;
import info.u_team.u_team_core.intern.network.DataHolderMenuMessage;
import info.u_team.u_team_core.intern.network.FluidClickContainerMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class UCoreNetworkForge {
	
	public static final String PROTOCOL = "1.19.4-1";
	
	public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation(UCoreMod.MODID, "network_forge"), () -> PROTOCOL, version -> {
		// Allow clients to join / view servers without errors when uteamcore is not present there
		return NetworkRegistry.acceptMissingOr(PROTOCOL).test(version);
	}, PROTOCOL::equals);
	
	public static void setup(FMLCommonSetupEvent event) {
		NETWORK.registerMessage(0x0, DataHolderMenuMessage.class, DataHolderMenuMessage::encode, DataHolderMenuMessage::decode, DataHolderMenuMessage.Handler::handle);
		NETWORK.registerMessage(0x1, ContainerSetFluidContentMessage.class, ContainerSetFluidContentMessage::encode, ContainerSetFluidContentMessage::decode, ContainerSetFluidContentMessage.Handler::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		NETWORK.registerMessage(0x2, ContainerSetFluidSlotMessage.class, ContainerSetFluidSlotMessage::encode, ContainerSetFluidSlotMessage::decode, ContainerSetFluidSlotMessage.Handler::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		NETWORK.registerMessage(0x3, FluidClickContainerMessage.class, FluidClickContainerMessage::encode, FluidClickContainerMessage::decode, FluidClickContainerMessage.Handler::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UCoreNetworkForge::setup);
	}
	
}

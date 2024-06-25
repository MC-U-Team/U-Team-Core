package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.UCoreReference;
import info.u_team.u_team_core.intern.network.ContainerSetFluidContentMessage;
import info.u_team.u_team_core.intern.network.ContainerSetFluidSlotMessage;
import info.u_team.u_team_core.intern.network.FluidClickContainerMessage;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class UCoreNetworkNeoForge {
	
	public static final String PROTOCOL = Integer.toString(UCoreReference.PROTOCOL_VERSION);
	
	private static void register(RegisterPayloadHandlersEvent event) {
		event.registrar(UCoreMod.MODID) //
				.versioned(PROTOCOL) //
				.playToClient(ContainerSetFluidContentMessage.TYPE, StreamCodec.of(ContainerSetFluidContentMessage::write, ContainerSetFluidContentMessage::read), ContainerSetFluidContentMessage.Handler::handle) //
				.playToClient(ContainerSetFluidSlotMessage.TYPE, StreamCodec.of(ContainerSetFluidSlotMessage::write, ContainerSetFluidSlotMessage::read), ContainerSetFluidSlotMessage.Handler::handle) //
				.playToClient(FluidClickContainerMessage.TYPE, StreamCodec.of(FluidClickContainerMessage::write, FluidClickContainerMessage::read), FluidClickContainerMessage.Handler::handle);
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UCoreNetworkNeoForge::register);
	}
	
}

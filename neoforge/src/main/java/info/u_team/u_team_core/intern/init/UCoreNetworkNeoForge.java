package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.UCoreReference;
import info.u_team.u_team_core.intern.network.ContainerSetFluidContentMessage;
import info.u_team.u_team_core.intern.network.ContainerSetFluidSlotMessage;
import info.u_team.u_team_core.intern.network.FluidClickContainerMessage;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;

public class UCoreNetworkNeoForge {
	
	public static final String PROTOCOL = Integer.toString(UCoreReference.PROTOCOL_VERSION);
	
	public static void register(RegisterPayloadHandlerEvent event) {
		event.registrar(UCoreMod.MODID) //
				.versioned(PROTOCOL) //
				.play(ContainerSetFluidContentMessage.ID, ContainerSetFluidContentMessage::read, ContainerSetFluidContentMessage.Handler::handle) //
				.play(ContainerSetFluidSlotMessage.ID, ContainerSetFluidSlotMessage::read, ContainerSetFluidSlotMessage.Handler::handle) //
				.play(FluidClickContainerMessage.ID, FluidClickContainerMessage::read, FluidClickContainerMessage.Handler::handle);
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UCoreNetworkNeoForge::register);
	}
	
}

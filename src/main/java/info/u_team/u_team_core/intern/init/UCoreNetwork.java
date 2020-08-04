package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.intern.network.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class UCoreNetwork {
	
	public static final String PROTOCOL = "1.15.2-2";
	
	public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation(UCoreMod.MODID, "network"), () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);
	
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

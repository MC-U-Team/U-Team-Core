package info.u_team.u_team_core.util.registry;

import java.util.function.Consumer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class BusRegistry {
	
	public static void registerMod(Consumer<IEventBus> consumer) {
		register(FMLJavaModLoadingContext.get().getModEventBus(), consumer);
	}
	
	public static void registerForge(Consumer<IEventBus> consumer) {
		register(MinecraftForge.EVENT_BUS, consumer);
	}
	
	public static void register(IEventBus bus, Consumer<IEventBus> consumer) {
		consumer.accept(bus);
	}
	
}

package info.u_team.u_team_core.util.registry;

import java.util.function.Consumer;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;

public class BusRegister {
	
	public static void registerMod(Consumer<IEventBus> consumer) {
		register(ModLoadingContext.get().getActiveContainer().getEventBus(), consumer);
	}
	
	public static void registerForge(Consumer<IEventBus> consumer) {
		register(NeoForge.EVENT_BUS, consumer);
	}
	
	public static void register(IEventBus bus, Consumer<IEventBus> consumer) {
		consumer.accept(bus);
	}
	
}

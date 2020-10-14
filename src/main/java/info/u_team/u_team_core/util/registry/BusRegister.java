package info.u_team.u_team_core.util.registry;

import java.util.function.Consumer;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class BusRegister {
	
	public static void registerMod(Consumer<IEventBus> consumer) {
		register(Bus.MOD, consumer);
	}
	
	public static void registerForge(Consumer<IEventBus> consumer) {
		register(Bus.FORGE, consumer);
	}
	
	public static void register(Bus bus, Consumer<IEventBus> consumer) {
		register(bus.bus().get(), consumer);
	}
	
	public static void register(IEventBus bus, Consumer<IEventBus> consumer) {
		consumer.accept(bus);
	}
	
}
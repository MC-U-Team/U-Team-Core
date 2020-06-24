package info.u_team.u_team_core.util.registry;

import java.util.function.Consumer;

import net.minecraftforge.eventbus.api.IEventBus;

public class BusRegistry {
	
	public static void register(IEventBus bus, Consumer<IEventBus> consumer) {
		consumer.accept(bus);
	}
	
}

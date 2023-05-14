package info.u_team.u_team_core.util;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Supplier;

import info.u_team.u_team_core.UCoreReference;

public class ServiceUtil {
	
	public static <I> I load(Class<I> serviceClass) {
		final List<I> services = ServiceLoader.load(serviceClass).stream().map(Supplier::get).toList();
		if (services.size() != 1) {
			throw new IllegalStateException("Only one service for class '" + serviceClass + "' is allowed. Currently there are the following services registered: " + services);
		}
		final I service = services.get(0);
		UCoreReference.LOGGER.debug("Loaded service {} for class {}", service, serviceClass);
		return service;
	}
	
}

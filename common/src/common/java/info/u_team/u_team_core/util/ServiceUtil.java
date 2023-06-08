package info.u_team.u_team_core.util;

import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.function.Supplier;

import info.u_team.u_team_core.UCoreReference;

public class ServiceUtil {
	
	public static <I> I loadOne(Class<I> serviceClass) {
		final List<I> services = find(serviceClass);
		if (services.size() != 1) {
			throw new IllegalStateException("Only one service for class '" + serviceClass + "' is allowed. Currently there are the following services registered: " + services);
		}
		final I service = services.get(0);
		UCoreReference.LOGGER.debug("Loaded service {} for class {}", service, serviceClass);
		return service;
	}
	
	public static <I> List<I> loadAll(Class<I> serviceClass) {
		final List<I> services = find(serviceClass);
		if (!services.isEmpty()) {
			UCoreReference.LOGGER.debug("Loaded services {} for class {}", services, serviceClass);
		}
		return services;
	}
	
	private static <I> List<I> find(Class<I> serviceClass) {
		try {
			return ServiceLoader.load(serviceClass).stream().map(Supplier::get).toList();
		} catch (final ServiceConfigurationError ex) {
			throw new IllegalStateException("An error occured while resolving services for class '" + serviceClass + "'", ex);
		}
	}
	
}

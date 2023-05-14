package info.u_team.u_team_core.util;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Supplier;

public class ServiceUtil {
	
	public static <I> I load(Class<I> service) {
		final List<I> services = ServiceLoader.load(service).stream().map(Supplier::get).toList();
		if (services.size() != 1) {
			throw new IllegalStateException("Only one service for class '" + service + "' is allowed. Currently there are the following services registered: " + services);
		}
		return services.get(0);
	}
	
}

package info.u_team.u_team_core.util;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.Platform;
import info.u_team.u_team_core.api.Platform.Environment;

public class EnvironmentUtil {
	
	public static void runWhen(Environment type, Supplier<Runnable> supplier) {
		if (type == Platform.getInstance().getEnvironment()) {
			supplier.get().run();
		}
	}
	
	public static <V> V callWhen(Environment type, Supplier<Callable<V>> supplier) {
		if (type == Platform.getInstance().getEnvironment()) {
			try {
				return supplier.get().call();
			} catch (final Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		return null;
	}
	
}

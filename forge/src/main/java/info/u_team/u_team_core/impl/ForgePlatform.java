package info.u_team.u_team_core.impl;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.Platform;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class ForgePlatform implements Platform {
	
	private final Supplier<Environment> environment = Suppliers.memoize(() -> {
		return switch (FMLEnvironment.dist) {
		case CLIENT -> Environment.CLIENT;
		case DEDICATED_SERVER -> Environment.SERVER;
		};
	});
	
	@Override
	public Environment getEnvironment() {
		return environment.get();
	}
}

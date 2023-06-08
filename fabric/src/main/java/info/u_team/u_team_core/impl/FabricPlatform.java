package info.u_team.u_team_core.impl;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.Platform;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatform implements Platform {
	
	private final Supplier<Environment> environment = Suppliers.memoize(() -> {
		return switch (FabricLoader.getInstance().getEnvironmentType()) {
		case CLIENT -> Environment.CLIENT;
		case SERVER -> Environment.SERVER;
		};
	});
	
	@Override
	public Environment getEnvironment() {
		return environment.get();
	}
	
	@Override
	public boolean isModLoaded(String modid) {
		return FabricLoader.getInstance().isModLoaded(modid);
	}
}

package info.u_team.u_team_core.impl;

import java.nio.file.Path;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.Platform;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;

public class NeoForgePlatform implements Platform {
	
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
	
	@Override
	public boolean isModLoaded(String modid) {
		return ModList.get().isLoaded(modid);
	}
	
	@Override
	public Path getConfigPath() {
		return FMLPaths.CONFIGDIR.get();
	}
}

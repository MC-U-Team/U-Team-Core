package info.u_team.u_team_core.api.registry.client;

import java.util.function.Supplier;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.client.KeyMapping;

public interface KeyMappingRegister {
	
	KeyMappingRegister INSTANCE = ServiceUtil.loadOne(KeyMappingRegister.class);
	
	static Supplier<KeyMapping> register(Supplier<KeyMapping> supplier) {
		return KeyMappingRegister.INSTANCE.registerKeyMapping(supplier);
	}
	
	Supplier<KeyMapping> registerKeyMapping(Supplier<KeyMapping> supplier);
}

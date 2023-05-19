package info.u_team.u_team_core.api.registry.client;

import java.util.function.Supplier;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.client.KeyMapping;

public interface KeyMappingRegister {
	
	static KeyMappingRegister create() {
		return Factory.INSTANCE.create();
	}
	
	Supplier<KeyMapping> register(Supplier<KeyMapping> supplier);
	
	void register();
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
		
		KeyMappingRegister create();
	}
}

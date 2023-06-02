package info.u_team.u_team_core.api.registry;

import java.util.function.Supplier;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public interface EntityAttributeRegister {
	
	static EntityAttributeRegister create() {
		return Factory.INSTANCE.create();
	}
	
	default void register(Supplier<? extends EntityType<? extends LivingEntity>> supplier, AttributeSupplier.Builder builder) {
		register(supplier, builder.build());
	}
	
	void register(Supplier<? extends EntityType<? extends LivingEntity>> supplier, AttributeSupplier map);
	
	void register();
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
		
		EntityAttributeRegister create();
	}
	
}

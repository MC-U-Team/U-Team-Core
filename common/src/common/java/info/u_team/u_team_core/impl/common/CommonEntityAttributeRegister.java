package info.u_team.u_team_core.impl.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.EntityAttributeRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public abstract class CommonEntityAttributeRegister implements EntityAttributeRegister {
	
	protected final Map<Supplier<? extends EntityType<? extends LivingEntity>>, AttributeSupplier> attributes;
	
	protected CommonEntityAttributeRegister() {
		attributes = new HashMap<>();
	}
	
	@Override
	public void register(Supplier<? extends EntityType<? extends LivingEntity>> supplier, AttributeSupplier map) {
		attributes.put(supplier, map);
	}
	
}

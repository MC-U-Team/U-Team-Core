package info.u_team.u_team_core.util.registry;

import java.util.function.Supplier;

import net.minecraft.entity.*;
import net.minecraft.entity.EntityType.Builder;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeDeferredRegister {
	
	public static EntityTypeDeferredRegister create(String modid) {
		return new EntityTypeDeferredRegister(modid);
	}
	
	private final CommonDeferredRegister<EntityType<?>> register;
	
	protected EntityTypeDeferredRegister(String modid) {
		register = CommonDeferredRegister.create(ForgeRegistries.ENTITIES, modid);
	}
	
	public <E extends Entity> RegistryObject<EntityType<E>> register(String name, Supplier<Builder<E>> supplier) {
		return register.register(name, () -> supplier.get().build(register.getModid() + ":" + name));
	}
	
	public void register(IEventBus bus) {
		register.register(bus);
	}
	
	public CommonDeferredRegister<EntityType<?>> getRegister() {
		return register;
	}
	
}

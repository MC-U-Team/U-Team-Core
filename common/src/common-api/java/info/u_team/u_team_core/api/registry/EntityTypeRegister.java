package info.u_team.u_team_core.api.registry;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public interface EntityTypeRegister extends Iterable<RegistryEntry<EntityType<?>>> {
	
	static EntityTypeRegister create(String modid) {
		return new EntityTypeRegister() {
			
			private final CommonRegister<EntityType<?>> register = CommonRegister.create(Registries.ENTITY_TYPE, modid);
			
			private static final String DUMMY_ENTRY = "egg"; // Pass a vanilla minecraft entity here, so we don't get the complain about a missing data fixer
			
			@Override
			public <T extends Entity> RegistryEntry<EntityType<T>> register(String name, Function<ResourceLocation, EntityType.Builder<T>> function) {
				return register.register(name, location -> function.apply(location).build(DUMMY_ENTRY));
			}
			
			@Override
			public <T extends Entity> RegistryEntry<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> supplier) {
				return register.register(name, () -> supplier.get().build(DUMMY_ENTRY));
			}
			
			@Override
			public void register() {
				register.register();
			}
			
			@Override
			public String getModid() {
				return register.getModid();
			}
			
			@Override
			public ResourceKey<? extends Registry<EntityType<?>>> getRegistryKey() {
				return register.getRegistryKey();
			}
			
			@Override
			public Collection<RegistryEntry<EntityType<?>>> getEntries() {
				return register.getEntries();
			}
			
			@Override
			public Iterator<RegistryEntry<EntityType<?>>> iterator() {
				return register.iterator();
			}
			
			@Override
			public Iterable<EntityType<?>> entryIterable() {
				return register.entryIterable();
			}
			
			@Override
			public CommonRegister<EntityType<?>> getCommonRegister() {
				return register;
			}
		};
	}
	
	<T extends Entity> RegistryEntry<EntityType<T>> register(String name, Function<ResourceLocation, EntityType.Builder<T>> supplier);
	
	<T extends Entity> RegistryEntry<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> supplier);
	
	void register();
	
	String getModid();
	
	ResourceKey<? extends Registry<EntityType<?>>> getRegistryKey();
	
	Collection<RegistryEntry<EntityType<?>>> getEntries();
	
	Iterable<EntityType<?>> entryIterable();
	
	CommonRegister<EntityType<?>> getCommonRegister();
}

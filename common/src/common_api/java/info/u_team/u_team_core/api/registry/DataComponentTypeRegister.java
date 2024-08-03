package info.u_team.u_team_core.api.registry;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public interface DataComponentTypeRegister extends Iterable<RegistryEntry<DataComponentType<?>>> {
	
	static DataComponentTypeRegister create(String modid) {
		return new DataComponentTypeRegister() {
			
			private final CommonRegister<DataComponentType<?>> register = CommonRegister.create(Registries.DATA_COMPONENT_TYPE, modid);
			
			@Override
			public <T> RegistryEntry<DataComponentType<T>> register(String name, Function<ResourceLocation, DataComponentType.Builder<T>> function) {
				return register.register(name, location -> function.apply(location).build());
			}
			
			@Override
			public <T> RegistryEntry<DataComponentType<T>> register(String name, Supplier<DataComponentType.Builder<T>> supplier) {
				return register.register(name, () -> supplier.get().build());
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
			public ResourceKey<? extends Registry<DataComponentType<?>>> getRegistryKey() {
				return register.getRegistryKey();
			}
			
			@Override
			public Collection<RegistryEntry<DataComponentType<?>>> getEntries() {
				return register.getEntries();
			}
			
			@Override
			public Iterator<RegistryEntry<DataComponentType<?>>> iterator() {
				return register.iterator();
			}
			
			@Override
			public Iterable<DataComponentType<?>> entryIterable() {
				return register.entryIterable();
			}
			
			@Override
			public CommonRegister<DataComponentType<?>> getCommonRegister() {
				return register;
			}
		};
	}
	
	<T> RegistryEntry<DataComponentType<T>> register(String name, Function<ResourceLocation, DataComponentType.Builder<T>> function);
	
	<T> RegistryEntry<DataComponentType<T>> register(String name, Supplier<DataComponentType.Builder<T>> supplier);
	
	void register();
	
	String getModid();
	
	ResourceKey<? extends Registry<DataComponentType<?>>> getRegistryKey();
	
	Collection<RegistryEntry<DataComponentType<?>>> getEntries();
	
	Iterable<DataComponentType<?>> entryIterable();
	
	CommonRegister<DataComponentType<?>> getCommonRegister();
}

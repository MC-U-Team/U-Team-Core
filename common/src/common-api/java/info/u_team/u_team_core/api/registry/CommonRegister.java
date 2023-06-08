package info.u_team.u_team_core.api.registry;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.Streams;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public interface CommonRegister<R> extends Iterable<RegistryEntry<R>> {
	
	static <C> CommonRegister<C> create(ResourceKey<? extends Registry<C>> key, String modid) {
		return Factory.INSTANCE.create(key, modid);
	}
	
	<E extends R> RegistryEntry<E> register(String name, Function<ResourceLocation, ? extends E> function);
	
	<E extends R> RegistryEntry<E> register(String name, Supplier<? extends E> supplier);
	
	void register();
	
	String getModid();
	
	ResourceKey<? extends Registry<R>> getRegistryKey();
	
	Collection<RegistryEntry<R>> getEntries();
	
	@Override
	default Iterator<RegistryEntry<R>> iterator() {
		return getEntries().iterator();
	}
	
	default Iterable<R> entryIterable() {
		return () -> Streams.stream(this).map(RegistryEntry::get).iterator();
	}
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
		
		<C> CommonRegister<C> create(ResourceKey<? extends Registry<C>> key, String modid);
	}
	
}

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
	
	public static <C> CommonRegister<C> create(ResourceKey<? extends Registry<C>> key, String modid) {
		return Factory.INSTANCE.create(key, modid);
	}
	
	public <E extends R> RegistryEntry<E> register(String name, Function<ResourceLocation, ? extends E> function);
	
	public <E extends R> RegistryEntry<E> register(String name, Supplier<? extends E> supplier);
	
	public void register();
	
	public String getModid();
	
	public Collection<RegistryEntry<R>> getEntries();
	
	@Override
	public default Iterator<RegistryEntry<R>> iterator() {
		return getEntries().iterator();
	}
	
	public default Iterable<R> entryIterable() {
		return () -> Streams.stream(this).map(RegistryEntry::get).iterator();
	}
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.load(Factory.class);
		
		<C> CommonRegister<C> create(ResourceKey<? extends Registry<C>> key, String modid);
		
	}
	
}

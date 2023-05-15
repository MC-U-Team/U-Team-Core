package info.u_team.u_team_core.factory;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ForgeCommonRegister<C> implements CommonRegister<C> {
	
	private final DeferredRegister<C> register;
	private final String modid;
	
	ForgeCommonRegister(ResourceKey<? extends Registry<C>> key, String modid) {
		register = DeferredRegister.create(key, modid);
		this.modid = modid;
	}
	
	@Override
	public <E extends C> ForgeRegistryEntry<E> register(String name, Function<ResourceLocation, ? extends E> function) {
		return register(name, () -> function.apply(new ResourceLocation(modid, name)));
	}
	
	@Override
	public <E extends C> ForgeRegistryEntry<E> register(String name, Supplier<? extends E> supplier) {
		return new ForgeRegistryEntry<>(register.register(name, supplier));
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(register::register);
	}
	
	@Override
	public String getModid() {
		return modid;
	}
	
	@Override
	public ResourceKey<? extends Registry<C>> getRegistryKey() {
		return register.getRegistryKey();
	}
	
	@Override
	public Collection<RegistryEntry<C>> getEntries() {
		return register.getEntries().stream().map(object -> new ForgeRegistryEntry<>(object)).collect(Collectors.toUnmodifiableSet());
	}
	
	public DeferredRegister<C> getDeferredRegister() {
		return register;
	}
	
	public static class ForgeRegistryEntry<E> implements RegistryEntry<E> {
		
		private final RegistryObject<E> object;
		
		ForgeRegistryEntry(RegistryObject<E> object) {
			this.object = object;
		}
		
		@Override
		public E get() {
			return object.get();
		}
		
		@Override
		public ResourceLocation getId() {
			return object.getId();
		}
		
		@Override
		public ResourceKey<E> getKey() {
			return object.getKey();
		}
		
		@Override
		public Optional<Holder<E>> getHolder() {
			return object.getHolder();
		}
		
		@Override
		public boolean isPresent() {
			return object.isPresent();
		}
		
		public RegistryObject<E> getRegistryObject() {
			return object;
		}
	}
	
	public static class Factory implements CommonRegister.Factory {
		
		@Override
		public <C> CommonRegister<C> create(ResourceKey<? extends Registry<C>> key, String modid) {
			return new ForgeCommonRegister<>(key, modid);
		}
	}
}

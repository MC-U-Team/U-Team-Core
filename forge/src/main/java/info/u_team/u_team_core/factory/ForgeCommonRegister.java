package info.u_team.u_team_core.factory;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

public class ForgeCommonRegister<C> implements CommonRegister<C> {
	
	private final CommonDeferredRegister<C> register;
	
	private ForgeCommonRegister(ResourceKey<? extends Registry<C>> key, String modid) {
		register = CommonDeferredRegister.create(key, modid);
	}
	
	@Override
	public <E extends C> RegistryEntry<E> register(String name, Function<ResourceLocation, ? extends E> function) {
		return new ForgeRegistryEntry<>(register.register(name, function));
	}
	
	@Override
	public <E extends C> RegistryEntry<E> register(String name, Supplier<? extends E> supplier) {
		return new ForgeRegistryEntry<>(register.register(name, supplier));
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(register::register);
	}
	
	@Override
	public String getModid() {
		return register.getModid();
	}
	
	@Override
	public Collection<RegistryEntry<C>> getEntries() {
		return register.getEntries().stream().map(object -> new ForgeRegistryEntry<>(object)).collect(Collectors.toUnmodifiableSet());
	}
	
	public class ForgeRegistryEntry<E> implements RegistryEntry<E> {
		
		private final RegistryObject<E> object;
		
		private ForgeRegistryEntry(RegistryObject<E> object) {
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
		
	}
	
	public static class Factory implements CommonRegister.Factory {
		
		@Override
		public <C> CommonRegister<C> create(ResourceKey<? extends Registry<C>> key, String modid) {
			return new ForgeCommonRegister<>(key, modid);
		}
		
	}
	
}

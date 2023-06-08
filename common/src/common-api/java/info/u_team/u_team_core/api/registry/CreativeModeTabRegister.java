package info.u_team.u_team_core.api.registry;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public interface CreativeModeTabRegister extends Iterable<RegistryEntry<CreativeModeTab>> {
	
	static CreativeModeTabRegister create(String modid) {
		return new CreativeModeTabRegister() {
			
			private final CommonRegister<CreativeModeTab> register = CommonRegister.create(Registries.CREATIVE_MODE_TAB, modid);
			
			@Override
			public RegistryEntry<CreativeModeTab> register(String name, BiFunction<ResourceLocation, CreativeModeTab.Builder, CreativeModeTab.Builder> function) {
				return registerBuilder(name, location -> function.apply(location, Builder.INSTANCE.create()));
			}
			
			@Override
			public RegistryEntry<CreativeModeTab> register(String name, UnaryOperator<CreativeModeTab.Builder> function) {
				return registerBuilder(name, () -> function.apply(Builder.INSTANCE.create()));
			}
			
			@Override
			public RegistryEntry<CreativeModeTab> registerBuilder(String name, Function<ResourceLocation, CreativeModeTab.Builder> function) {
				return register.register(name, location -> function.apply(location).build());
			}
			
			@Override
			public RegistryEntry<CreativeModeTab> registerBuilder(String name, Supplier<CreativeModeTab.Builder> supplier) {
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
			public ResourceKey<? extends Registry<CreativeModeTab>> getRegistryKey() {
				return register.getRegistryKey();
			}
			
			@Override
			public Collection<RegistryEntry<CreativeModeTab>> getEntries() {
				return register.getEntries();
			}
			
			@Override
			public Iterator<RegistryEntry<CreativeModeTab>> iterator() {
				return register.iterator();
			}
			
			@Override
			public Iterable<CreativeModeTab> entryIterable() {
				return register.entryIterable();
			}
			
			@Override
			public CommonRegister<CreativeModeTab> getCommonRegister() {
				return register;
			}
		};
	}
	
	RegistryEntry<CreativeModeTab> register(String name, BiFunction<ResourceLocation, CreativeModeTab.Builder, CreativeModeTab.Builder> function);
	
	RegistryEntry<CreativeModeTab> register(String name, UnaryOperator<CreativeModeTab.Builder> function);
	
	RegistryEntry<CreativeModeTab> registerBuilder(String name, Function<ResourceLocation, CreativeModeTab.Builder> function);
	
	RegistryEntry<CreativeModeTab> registerBuilder(String name, Supplier<CreativeModeTab.Builder> supplier);
	
	void register();
	
	String getModid();
	
	ResourceKey<? extends Registry<CreativeModeTab>> getRegistryKey();
	
	Collection<RegistryEntry<CreativeModeTab>> getEntries();
	
	Iterable<CreativeModeTab> entryIterable();
	
	CommonRegister<CreativeModeTab> getCommonRegister();
	
	interface Builder {
		
		Builder INSTANCE = ServiceUtil.loadOne(Builder.class);
		
		CreativeModeTab.Builder create();
	}
}

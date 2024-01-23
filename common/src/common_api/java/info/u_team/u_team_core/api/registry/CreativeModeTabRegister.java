package info.u_team.u_team_core.api.registry;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public interface CreativeModeTabRegister extends Iterable<RegistryEntry<CreativeModeTab>> {
	
	static CreativeModeTabRegister create(String modid) {
		return new CreativeModeTabRegister() {
			
			private final CommonRegister<CreativeModeTab> register = CommonRegister.create(Registries.CREATIVE_MODE_TAB, modid);
			
			@Override
			public RegistryEntry<CreativeModeTab> register(String name, BiConsumer<ResourceLocation, CreativeModeTab.Builder> consumer) {
				return register(name, location -> {
					final CreativeModeTab.Builder builder = createDefaultBuilder(location);
					consumer.accept(location, builder);
					return builder;
				});
			}
			
			@Override
			public RegistryEntry<CreativeModeTab> register(String name, Consumer<CreativeModeTab.Builder> consumer) {
				return register(name, location -> {
					final CreativeModeTab.Builder builder = createDefaultBuilder(location);
					consumer.accept(builder);
					return builder;
				});
			}
			
			private static CreativeModeTab.Builder createDefaultBuilder(ResourceLocation location) {
				final CreativeModeTab.Builder builder = Builder.INSTANCE.create();
				builder.title(Component.translatable("creativetabs.%s.%s".formatted(location.getNamespace(), location.getPath())));
				return builder;
			}
			
			@Override
			public RegistryEntry<CreativeModeTab> register(String name, Function<ResourceLocation, CreativeModeTab.Builder> function) {
				return register.register(name, location -> function.apply(location).build());
			}
			
			@Override
			public RegistryEntry<CreativeModeTab> register(String name, Supplier<CreativeModeTab.Builder> supplier) {
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
	
	RegistryEntry<CreativeModeTab> register(String name, BiConsumer<ResourceLocation, CreativeModeTab.Builder> consumer);
	
	RegistryEntry<CreativeModeTab> register(String name, Consumer<CreativeModeTab.Builder> consumer);
	
	RegistryEntry<CreativeModeTab> register(String name, Function<ResourceLocation, CreativeModeTab.Builder> function);
	
	RegistryEntry<CreativeModeTab> register(String name, Supplier<CreativeModeTab.Builder> supplier);
	
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

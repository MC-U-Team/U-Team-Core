package info.u_team.u_team_core.util.registry;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

public class CommonDeferredRegister<R extends IForgeRegistryEntry<R>> implements Iterable<RegistryObject<R>> {
	
	public static <C extends IForgeRegistryEntry<C>> CommonDeferredRegister<C> create(ResourceKey<? extends Registry<C>> key, String modid) {
		return new CommonDeferredRegister<>(key, modid);
	}
	
	public static <C extends IForgeRegistryEntry<C>> CommonDeferredRegister<C> create(IForgeRegistry<C> registry, String modid) {
		return new CommonDeferredRegister<>(registry, modid);
	}
	
	private final String modid;
	
	private final DeferredRegister<R> register;
	
	protected CommonDeferredRegister(ResourceKey<? extends Registry<R>> key, String modid) {
		this.modid = modid;
		register = DeferredRegister.create(key, modid);
	}
	
	protected CommonDeferredRegister(IForgeRegistry<R> registry, String modid) {
		this.modid = modid;
		register = DeferredRegister.create(registry, modid);
	}
	
	public <E extends R> RegistryObject<E> register(String name, Function<ResourceLocation, ? extends E> function) {
		return register(name, () -> function.apply(new ResourceLocation(modid, name)));
	}
	
	public <E extends R> RegistryObject<E> register(String name, Supplier<? extends E> supplier) {
		return register.register(name, supplier);
	}
	
	public void register(IEventBus bus) {
		register.register(bus);
	}
	
	public String getModid() {
		return modid;
	}
	
	public Collection<RegistryObject<R>> getEntries() {
		return register.getEntries();
	}
	
	@Override
	public Iterator<RegistryObject<R>> iterator() {
		return getEntries().iterator();
	}
	
	public DeferredRegister<R> getRegister() {
		return register;
	}
	
}

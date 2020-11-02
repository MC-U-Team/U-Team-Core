package info.u_team.u_team_core.util.registry;

import java.util.*;
import java.util.function.*;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

public class CommonDeferredRegister<R extends IForgeRegistryEntry<R>> implements Iterable<RegistryObject<R>> {
	
	public static <C extends IForgeRegistryEntry<C>> CommonDeferredRegister<C> create(IForgeRegistry<C> registry, String modid) {
		return new CommonDeferredRegister<C>(registry, modid);
	}
	
	private final String modid;
	
	private final DeferredRegister<R> register;
	
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
		return register.getEntries().iterator();
	}
	
}

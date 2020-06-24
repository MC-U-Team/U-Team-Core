package info.u_team.u_team_core.util.registry;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

public class CommonDeferedRegister<T extends IForgeRegistryEntry<T>> {
	
	public static <B extends IForgeRegistryEntry<B>> CommonDeferedRegister<B> create(IForgeRegistry<B> registry, String modid) {
		return new CommonDeferedRegister<B>(registry, modid);
	}
	
	private final IForgeRegistry<T> type;
	private final String modid;
	private final Map<RegistryObject<T>, Supplier<? extends T>> entries = new LinkedHashMap<>();
	private final Set<RegistryObject<T>> entriesView = Collections.unmodifiableSet(entries.keySet());
	
	public CommonDeferedRegister(IForgeRegistry<T> reg, String modid) {
		this.type = reg;
		this.modid = modid;
	}
	
	@SuppressWarnings("unchecked")
	public <I extends T> RegistryObject<I> register(final String name, final Supplier<? extends I> sup) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(sup);
		final ResourceLocation key = new ResourceLocation(modid, name);
		RegistryObject<I> ret = RegistryObject.of(key, this.type);
		if (entries.putIfAbsent((RegistryObject<T>) ret, () -> sup.get().setRegistryName(key)) != null) {
			throw new IllegalArgumentException("Duplicate registration " + name);
		}
		return ret;
	}
	
	public void register(IEventBus bus) {
		bus.addListener(this::addEntries);
	}
	
	public Collection<RegistryObject<T>> getEntries() {
		return entriesView;
	}
	
	private void addEntries(RegistryEvent.Register<?> event) {
		if (event.getGenericType() == this.type.getRegistrySuperType()) {
			@SuppressWarnings("unchecked")
			IForgeRegistry<T> reg = (IForgeRegistry<T>) event.getRegistry();
			for (Entry<RegistryObject<T>, Supplier<? extends T>> e : entries.entrySet()) {
				reg.register(e.getValue().get());
				e.getKey().updateReference(reg);
			}
		}
	}
}
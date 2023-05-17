package info.u_team.u_team_core.api.registry;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.Streams;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public interface CreativeModeTabRegister extends Iterable<ResourceEntry<CreativeModeTab>> {
	
	static CreativeModeTabRegister create(String modid) {
		return Factory.INSTANCE.create(modid);
	}
	
	ResourceEntry<CreativeModeTab> register(String name, BiConsumer<ResourceLocation, CreativeModeTab.Builder> consumer);
	
	ResourceEntry<CreativeModeTab> register(String name, Consumer<CreativeModeTab.Builder> consumer);
	
	void register();
	
	String getModid();
	
	Collection<ResourceEntry<CreativeModeTab>> getEntries();
	
	@Override
	default Iterator<ResourceEntry<CreativeModeTab>> iterator() {
		return getEntries().iterator();
	}
	
	default Iterable<CreativeModeTab> entryIterable() {
		return () -> Streams.stream(this).map(Supplier::get).iterator();
	}
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
		
		CreativeModeTabRegister create(String modid);
	}
	
}

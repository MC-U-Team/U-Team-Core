package info.u_team.u_team_core.api.registry;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface BlockEntityTypeRegister extends Iterable<RegistryEntry<BlockEntityType<?>>> {
	
	static BlockEntityTypeRegister create(String modid) {
		return new BlockEntityTypeRegister() {
			
			private final CommonRegister<BlockEntityType<?>> register = CommonRegister.create(Registries.BLOCK_ENTITY_TYPE, modid);
			
			@Override
			public <T extends BlockEntity> RegistryEntry<BlockEntityType<T>> register(String name, Function<ResourceLocation, BlockEntityType.Builder<T>> function) {
				return register.register(name, location -> function.apply(location).build(null));
			}
			
			@Override
			public <T extends BlockEntity> RegistryEntry<BlockEntityType<T>> register(String name, Supplier<BlockEntityType.Builder<T>> supplier) {
				return register.register(name, () -> supplier.get().build(null));
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
			public ResourceKey<? extends Registry<BlockEntityType<?>>> getRegistryKey() {
				return register.getRegistryKey();
			}
			
			@Override
			public Collection<RegistryEntry<BlockEntityType<?>>> getEntries() {
				return register.getEntries();
			}
			
			@Override
			public Iterator<RegistryEntry<BlockEntityType<?>>> iterator() {
				return register.iterator();
			}
			
			@Override
			public Iterable<BlockEntityType<?>> entryIterable() {
				return register.entryIterable();
			}
			
			@Override
			public CommonRegister<BlockEntityType<?>> getCommonRegister() {
				return register;
			}
		};
	}
	
	<T extends BlockEntity> RegistryEntry<BlockEntityType<T>> register(String name, Function<ResourceLocation, BlockEntityType.Builder<T>> supplier);
	
	<T extends BlockEntity> RegistryEntry<BlockEntityType<T>> register(String name, Supplier<BlockEntityType.Builder<T>> supplier);
	
	void register();
	
	String getModid();
	
	ResourceKey<? extends Registry<BlockEntityType<?>>> getRegistryKey();
	
	Collection<RegistryEntry<BlockEntityType<?>>> getEntries();
	
	Iterable<BlockEntityType<?>> entryIterable();
	
	CommonRegister<BlockEntityType<?>> getCommonRegister();
}

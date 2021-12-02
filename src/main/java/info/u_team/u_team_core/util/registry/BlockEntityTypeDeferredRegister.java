package info.u_team.u_team_core.util.registry;

import java.util.function.Supplier;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityTypeDeferredRegister {
	
	public static BlockEntityTypeDeferredRegister create(String modid) {
		return new BlockEntityTypeDeferredRegister(modid);
	}
	
	private final CommonDeferredRegister<BlockEntityType<?>> register;
	
	protected BlockEntityTypeDeferredRegister(String modid) {
		register = CommonDeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, modid);
	}
	
	public <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, Supplier<Builder<T>> supplier) {
		return register.register(name, () -> supplier.get().build(null));
	}
	
	public void register(IEventBus bus) {
		register.register(bus);
	}
	
	public CommonDeferredRegister<BlockEntityType<?>> getRegister() {
		return register;
	}
	
}

package info.u_team.u_team_core.util.registry;

import java.util.function.Supplier;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeDeferredRegister {
	
	public static TileEntityTypeDeferredRegister create(String modid) {
		return new TileEntityTypeDeferredRegister(modid);
	}
	
	private final CommonDeferredRegister<BlockEntityType<?>> register;
	
	protected TileEntityTypeDeferredRegister(String modid) {
		register = CommonDeferredRegister.create(ForgeRegistries.TILE_ENTITIES, modid);
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

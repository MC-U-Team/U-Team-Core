package info.u_team.u_team_core.util.registry;

import java.util.function.Supplier;

import com.mojang.datafixers.DataFixUtils;

import net.minecraft.tileentity.*;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeDeferredRegister {
	
	public static TileEntityTypeDeferredRegister create(String modid) {
		return new TileEntityTypeDeferredRegister(modid);
	}
	
	private final CommonDeferredRegister<TileEntityType<?>> register;
	
	protected TileEntityTypeDeferredRegister(String modid) {
		register = CommonDeferredRegister.create(ForgeRegistries.TILE_ENTITIES, modid);
	}
	
	public <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<Builder<T>> supplier) {
		return register.register(name, () -> supplier.get().build(DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getVersion().getWorldVersion())).getChoiceType(TypeReferences.BLOCK_ENTITY, register.getModid() + ":" + name)));
	}
	
	public void register(IEventBus bus) {
		register.register(bus);
	}
	
	public CommonDeferredRegister<TileEntityType<?>> getRegister() {
		return register;
	}
	
}

package info.u_team.u_team_core.util.registry;

import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.IUBlockRegistryType;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

public class BlockDeferredRegister {
	
	public final DeferredRegister<Block> blocks;
	
	public BlockDeferredRegister(String modid) {
		blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
	}
	
	public <B extends Block & IUBlockRegistryType, I extends BlockItem> BlockRegistryObject<B, I> register(String name, Supplier<? extends B> supplier) {
		final RegistryObject<B> block = blocks.register(name, supplier);
		final RegistryObject<I> item = RegistryObject.of(new ResourceLocation(name), ForgeRegistries.ITEMS);
		
		return new BlockRegistryObject<B, I>(block, item);
	}
	
	public void register(IEventBus bus) {
		blocks.register(bus);
	}
	
}

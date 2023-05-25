package info.u_team.u_team_core.api.registry.client;

import java.util.function.Supplier;
import java.util.stream.Stream;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public interface ColorProviderRegister {
	
	static ColorProviderRegister create() {
		return Factory.INSTANCE.create();
	}
	
	void register(BlockRegisterHandler handler);
	
	void register(ItemRegisterHandler handler);
	
	void register();
	
	static interface BlockRegisterHandler {
		
		void registerColor(Supplier<BlockColors> blockColors, Register<BlockColor, Block> register);
	}
	
	static interface ItemRegisterHandler {
		
		void registerColor(Supplier<ItemColors> itemColors, Supplier<BlockColors> blockColors, Register<ItemColor, ItemLike> colorRegister);
	}
	
	static interface Register<T, E> {
		
		default void register(T color, E entry) {
			register(color, Stream.of(entry));
		}
		
		void register(T color, Stream<? extends E> entries);
	}
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
		
		ColorProviderRegister create();
	}
}

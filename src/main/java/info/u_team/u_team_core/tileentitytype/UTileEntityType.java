package info.u_team.u_team_core.tileentitytype;

import java.util.Set;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.types.Type;

import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.block.Block;
import net.minecraft.tileentity.*;

public class UTileEntityType<T extends TileEntity> extends TileEntityType<T> implements IURegistryType {
	
	protected final String name;
	
	protected UTileEntityType(String name, Supplier<? extends T> factory, Set<Block> validBlocks, Type<?> dataFixerType) {
		super(factory, validBlocks, dataFixerType);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	public static class UBuilder<T extends TileEntity> {
		
		private final String name;
		private final Supplier<? extends T> factory;
		private final Set<Block> blocks;
		private Type<?> dataFixerType;
		
		protected UBuilder(String name, Supplier<? extends T> factory, Set<Block> validBlocks) {
			this.name = name;
			this.factory = factory;
			this.blocks = validBlocks;
		}
		
		public static <T extends TileEntity> UBuilder<T> create(String name, Supplier<? extends T> factory, Block... validBlocks) {
			return new UBuilder<>(name, factory, ImmutableSet.copyOf(validBlocks));
		}
		
		public UBuilder<T> setDataFixerType(Type<?> dataFixerType) {
			this.dataFixerType = dataFixerType;
			return this;
		}
		
		public UTileEntityType<T> build() {
			return new UTileEntityType<>(name, factory, blocks, dataFixerType);
		}
	}
}

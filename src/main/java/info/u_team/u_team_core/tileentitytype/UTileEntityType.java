package info.u_team.u_team_core.tileentitytype;

import java.util.Set;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableSet;

import info.u_team.u_team_core.api.registry.IUTileEntityType;
import net.minecraft.block.Block;
import net.minecraft.tileentity.*;

public class UTileEntityType<T extends TileEntity> extends TileEntityType<T> implements IUTileEntityType {
	
	protected final String name;
	
	public UTileEntityType(String name, Supplier<? extends T> factory, Set<Block> validBlocks) {
		super(factory, validBlocks, null);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	public static class Builder<T extends TileEntity> {
		
		private final String name;
		private final Supplier<? extends T> factory;
		private final Set<Block> validBlocks;
		
		private Builder(String name, Supplier<? extends T> factory, Set<Block> validBlocks) {
			this.name = name;
			this.factory = factory;
			this.validBlocks = validBlocks;
		}
		
		public static <T extends TileEntity> Builder<T> create(String name, Supplier<? extends T> factory, Block... validBlocks) {
			return new Builder<>(name, factory, ImmutableSet.copyOf(validBlocks));
		}
		
		public TileEntityType<T> build() {
			return new UTileEntityType<>(name, factory, validBlocks);
		}
	}
	
}

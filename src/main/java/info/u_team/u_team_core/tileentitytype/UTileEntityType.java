package info.u_team.u_team_core.tileentitytype;

import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.IUTileEntityType;
import net.minecraft.tileentity.*;

public class UTileEntityType<T extends TileEntity> extends TileEntityType<T> implements IUTileEntityType {
	
	protected final String name;
	
	public UTileEntityType(String name, Supplier<? extends T> factory) {
		super(factory, null);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	public static class Builder<T extends TileEntity> {
		
		private final String name;
		private final Supplier<? extends T> factory;
		
		private Builder(String name, Supplier<? extends T> factory) {
			this.name = name;
			this.factory = factory;
		}
		
		public static <T extends TileEntity> Builder<T> create(String name, Supplier<? extends T> factory) {
			return new Builder<>(name, factory);
		}
		
		public TileEntityType<T> build() {
			return new UTileEntityType<>(name, factory);
		}
	}
	
}

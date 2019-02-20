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
	
}

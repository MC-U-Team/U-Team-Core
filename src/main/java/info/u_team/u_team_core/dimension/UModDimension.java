package info.u_team.u_team_core.dimension;

import java.util.function.Function;

import info.u_team.u_team_core.api.registry.IUModDimension;
import net.minecraft.world.dimension.*;
import net.minecraftforge.common.ModDimension;

public class UModDimension extends ModDimension implements IUModDimension {
	
	protected final String name;
	
	protected final Function<DimensionType, ? extends Dimension> function;
	
	public UModDimension(String name, Function<DimensionType, ? extends Dimension> function) {
		this.name = name;
		this.function = function;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	@Override
	public Function<DimensionType, ? extends Dimension> getFactory() {
		return function;
	}
	
}

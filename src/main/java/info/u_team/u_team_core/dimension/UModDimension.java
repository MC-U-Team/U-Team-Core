package info.u_team.u_team_core.dimension;

import java.util.function.BiFunction;

import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.world.World;
import net.minecraft.world.dimension.*;
import net.minecraftforge.common.ModDimension;

public class UModDimension extends ModDimension implements IURegistryType {
	
	protected final String name;
	
	private final BiFunction<World, DimensionType, ? extends Dimension> function;
	
	public UModDimension(String name, BiFunction<World, DimensionType, ? extends Dimension> function) {
		this.name = name;
		this.function = function;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	@Override
	public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
		return function;
	}
	
}

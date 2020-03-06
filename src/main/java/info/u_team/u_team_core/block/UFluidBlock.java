package info.u_team.u_team_core.block;

import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.IUBlockRegistryType;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.BlockItem;

public class UFluidBlock extends FlowingFluidBlock implements IUBlockRegistryType {
	
	protected final String name;
	
	public UFluidBlock(String name, Properties properties, Supplier<? extends FlowingFluid> flowingFluid) {
		super(flowingFluid, properties);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	@Override
	public BlockItem getBlockItem() {
		return null;
	}
}

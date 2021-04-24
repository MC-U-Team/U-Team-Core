package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UTileEntityBlock;
import info.u_team.u_team_test.init.TestItemGroups;
import info.u_team.u_team_test.init.TestTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class BasicFluidInventoryBlock extends UTileEntityBlock {
	
	public BasicFluidInventoryBlock() {
		super(TestItemGroups.GROUP, Properties.create(Material.IRON).hardnessAndResistance(1), TestTileEntityTypes.BASIC_FLUID_INVENTORY);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		return openContainer(world, pos, player, true);
	}
	
}

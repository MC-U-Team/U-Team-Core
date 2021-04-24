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

public class BasicEnergyCreatorBlock extends UTileEntityBlock {
	
	public BasicEnergyCreatorBlock() {
		super(TestItemGroups.GROUP, Properties.create(Material.IRON).hardnessAndResistance(2), TestTileEntityTypes.BASIC_ENERGY_CREATOR);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		return openContainer(world, pos, player, true);
	}
	
}

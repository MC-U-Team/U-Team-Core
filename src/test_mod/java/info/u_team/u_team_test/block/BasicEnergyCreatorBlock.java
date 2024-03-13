package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UTileEntityBlock;
import info.u_team.u_team_test.init.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class BasicEnergyCreatorBlock extends UTileEntityBlock {
	
	public BasicEnergyCreatorBlock(String name) {
		super(name, TestItemGroups.GROUP, Properties.create(Material.IRON).hardnessAndResistance(2), () -> TestTileEntityTypes.BASIC_ENERGY_CREATOR);
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		return openContainer(world, pos, player, true);
	}
	
}

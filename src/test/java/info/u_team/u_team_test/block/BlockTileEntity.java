package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_test.init.*;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTileEntity extends UBlockTileEntity {
	
	public BlockTileEntity(String name) {
		super(name, TestItemGroups.group, Properties.create(Material.ROCK).hardnessAndResistance(2F).sound(SoundType.GROUND).slipperiness(0.8F).lightValue(1), TestTileEntityTypes.tileentity);
	}
	
	@Override
	public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		return openContainer(world, pos, player, true);
	}
	
}

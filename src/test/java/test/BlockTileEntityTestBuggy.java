package test;

import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTileEntityTestBuggy extends UBlockTileEntity {
	
	public BlockTileEntityTestBuggy(String name) {
		super(name, Material.ROCK, TestMod.tab, new UTileEntityProvider(new ResourceLocation("test", "testtebuggy"), TileEntityTestBuggy.class));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return openContainer("test", 1, world, pos, player);
	}
	
}

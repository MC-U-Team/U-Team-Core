package test;

import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTileEntityTest extends UBlockTileEntity {
	
	public BlockTileEntityTest(String name) {
		super(name, Material.ROCK, TestMod.tab, new UTileEntityProvider(new ResourceLocation("test", "syncedte"), TileEntityTest.class));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return false;
		if (playerIn.isSneaking()) {
			((TileEntityTest) worldIn.getTileEntity(pos)).save();
		} else {
			playerIn.openGui("test", 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entityIn) {
		System.out.println("test");
		if (!(world instanceof World) || ((World) world).isRemote)
			return;
		((TileEntityTest) world.getTileEntity(pos)).increment();
	}
	
}

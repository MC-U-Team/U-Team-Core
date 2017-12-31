package info.u_team.u_team_core.block;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.item.UItemBlock;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Block API<br>
 * -> Block for TileEntities
 * 
 * @date 17.08.2017
 * @author MrTroble, HyCraftHD
 *
 */

public class UBlockTileEntity extends UBlock implements ITileEntityProvider {
	
	private UTileEntityProvider provider;
	
	public UBlockTileEntity(String name, Material material, UTileEntityProvider provider) {
		this(name, material, null, UItemBlock.class, provider);
	}
	
	public UBlockTileEntity(String name, Material material, Class<? extends UItemBlock> itemblock, UTileEntityProvider provider) {
		this(name, material, null, itemblock, provider);
	}
	
	public UBlockTileEntity(String name, Material material, UCreativeTab tab, UTileEntityProvider provider) {
		this(name, material, tab, UItemBlock.class, provider);
	}
	
	public UBlockTileEntity(String name, Material material, UCreativeTab tab, Class<? extends UItemBlock> itemblock, UTileEntityProvider provider) {
		super(name, material, tab, itemblock);
		this.provider = provider;
		this.isBlockContainer = true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
		super.eventReceived(state, world, pos, id, param);
		TileEntity tileentity = world.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return provider.create(world, meta);
	}
	
}

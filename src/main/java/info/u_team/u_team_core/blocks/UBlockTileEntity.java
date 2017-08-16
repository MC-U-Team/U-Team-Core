package info.u_team.u_team_core.blocks;

import javax.annotation.*;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.intern.UCoreConstants;
import info.u_team.u_team_core.item.UItemBlock;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Block API<br>
 * -> Block for TileEntity's
 * 
 * @date 17.08.2017
 * @author MrTroble
 *
 */

public class UBlockTileEntity extends UBlock implements ITileEntityProvider {
	
	private Class<? extends UTileEntity> clazz = null;
	
	public UBlockTileEntity(@Nonnull Material materialIn, @Nonnull String name, @Nullable UCreativeTab tab) {
		super(materialIn, name, tab);
	}
	
	public UBlockTileEntity(@Nonnull Material materialIn, @Nonnull String name, @Nullable UCreativeTab tab, @Nullable Class<? extends UItemBlock> clazz) {
		super(materialIn, name, tab, clazz);
	}
	
	public UBlockTileEntity(@Nullable Class<? extends UTileEntity> tileclazz, @Nonnull Material materialIn, @Nonnull String name, @Nullable UCreativeTab tab) {
		this(tileclazz, materialIn, name, tab, UItemBlock.class);
	}
	
	public UBlockTileEntity(@Nullable Class<? extends UTileEntity> tileclazz, @Nonnull Material materialIn, @Nonnull String name, @Nullable UCreativeTab tab, @Nullable Class<? extends UItemBlock> clazz) {
		super(materialIn, name, tab, clazz);
		this.clazz = tileclazz;
		if (this.clazz != null) {
			try {
				TileEntity.addMapping(this.clazz, this.clazz.getName());
			} catch (IllegalArgumentException ex) {
				UCoreConstants.LOGGER.warn("Tile used by multiple Blocks", ex);
			}
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if (clazz == null)
			return null;
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			UCoreConstants.LOGGER.error("Couldn't create TileEntity", e);
		}
		return null;
	}
	
}

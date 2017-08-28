package info.u_team.u_team_core.block;

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
 * -> Block for TileEntities
 * 
 * @date 17.08.2017
 * @author MrTroble, HyCraftHD
 *
 */

public class UBlockTileEntity extends UBlock implements ITileEntityProvider {
	
	private Class<? extends UTileEntity> tileentity = null;
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, Material material, String name) {
		super(material, name);
		register(tileentity);
	}
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, Material material, String name, Class<? extends UItemBlock> itemblock) {
		super(material, name, itemblock);
		register(tileentity);
	}
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, Material material, String name, UCreativeTab tab) {
		super(material, name, tab);
		register(tileentity);
	}
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, Material material, String name, UCreativeTab tab, Class<? extends UItemBlock> itemblock) {
		super(material, name, tab, itemblock);
		register(tileentity);
	}
	
	private final void register(Class<? extends UTileEntity> tileentity) {
		try {
			if (tileentity == null) {
				throw new NullPointerException("Tileentity is null.?");
			}
			TileEntity.addMapping(tileentity, tileentity.getName());
			this.tileentity = tileentity;
		} catch (Exception ex) {
			UCoreConstants.LOGGER.error("Error catched while registering tileentity.", ex);
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		try {
			return tileentity.newInstance();
		} catch (Exception ex) {
			UCoreConstants.LOGGER.error("Couldn't create tileentity object.", ex);
		}
		return null;
	}
	
}

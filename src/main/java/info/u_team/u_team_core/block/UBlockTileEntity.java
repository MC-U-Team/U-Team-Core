package info.u_team.u_team_core.block;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.intern.UCoreConstants;
import info.u_team.u_team_core.item.UItemBlock;
import info.u_team.u_team_core.sub.USub;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
	private String tileentityname = null;
	private Object[] objts = null;
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, String tileentityname, Material material, String name) {
		super(material, name);
		register(tileentity);
		this.tileentityname = tileentityname;
	}
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, String tileentityname, Material material, String name, Class<? extends UItemBlock> itemblock) {
		super(material, name, itemblock);
		register(tileentity);
		this.tileentityname = tileentityname;
	}
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, String tileentityname, Material material, String name, UCreativeTab tab) {
		super(material, name, tab);
		register(tileentity);
		this.tileentityname = tileentityname;
	}
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, String tileentityname, Material material, String name, UCreativeTab tab, Class<? extends UItemBlock> itemblock) {
		super(material, name, tab, itemblock);
		register(tileentity);
		this.tileentityname = tileentityname;
	}
	
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
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, String tileentityname, Material material, String name, Object... objects) {
		this(tileentity, tileentityname, material, name);
		this.objts = objects;
	}
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, String tileentityname, Material material, String name, Class<? extends UItemBlock> itemblock, Object... objects) {
		this(tileentity, tileentityname, material, name, itemblock);
		this.objts = objects;
	}
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, String tileentityname, Material material, String name, UCreativeTab tab, Object... objects) {
		this(tileentity, tileentityname, material, name, tab);
		this.objts = objects;
	}
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, String tileentityname, Material material, String name, UCreativeTab tab, Class<? extends UItemBlock> itemblock, Object... objects) {
		this(tileentity, tileentityname, material, name, tab, itemblock);
		this.objts = objects;
	}
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, Material material, String name, Object... objects) {
		this(tileentity, material, name);
		this.objts = objects;
	}
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, Material material, String name, Class<? extends UItemBlock> itemblock, Object... objects) {
		this(tileentity, material, name, itemblock);
		this.objts = objects;
	}
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, Material material, String name, UCreativeTab tab, Object... objects) {
		this(tileentity, material, name, tab);
		this.objts = objects;
	}
	
	public UBlockTileEntity(Class<? extends UTileEntity> tileentity, Material material, String name, UCreativeTab tab, Class<? extends UItemBlock> itemblock, Object... objects) {
		this(tileentity, material, name, tab, itemblock);
		this.objts = objects;
	}
	
	private final void register(Class<? extends UTileEntity> tileentity) {
		String name = tileentityname;
		if (name == null) {
			name = tileentity.getName();
		} else {
			name = USub.getID() + ":" + name;
		}
		GameRegistry.registerTileEntity(tileentity, name);
		this.tileentity = tileentity;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		try {
			if (objts == null) {
				return tileentity.newInstance();
			} else {
				Class<?>[] classes = new Class<?>[objts.length];
				int i = 0;
				for (Object ob : objts) {
					classes[i] = ob.getClass();
					i++;
				}
				tileentity.getConstructor(classes).newInstance(objts);
			}
		} catch (Exception ex) {
			UCoreConstants.LOGGER.error("Couldn't create tileentity object.", ex);
		}
		return null;
	}
	
}

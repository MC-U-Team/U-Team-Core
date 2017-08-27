package info.u_team.u_team_core.blocks;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.intern.UCoreConstants;
import info.u_team.u_team_core.item.UItemBlock;
import info.u_team.u_team_core.sub.USub;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Block API<br>
 * -> Basic Block
 * 
 * @date 17.08.2017
 * @author MrTroble
 */

public class UBlock extends Block {
	
	public UBlock(Material material, String name) {
		this(material, name, null, UItemBlock.class);
	}
	
	public UBlock(Material material, String name, Class<? extends UItemBlock> itemblock) {
		this(material, name, null, itemblock);
	}
	
	public UBlock(Material material, String name, UCreativeTab tab) {
		this(material, name, tab, UItemBlock.class);
	}
	
	public UBlock(Material material, String name, UCreativeTab tab, Class<? extends UItemBlock> itemblock) {
		super(material);
		
		this.setRegistryName(USub.getID(), name);
		this.setUnlocalizedName(name);
		
		if (tab != null) {
			this.setCreativeTab(tab);
		}
		
		register(itemblock);
	}
	
	private final void register(Class<? extends UItemBlock> itemblock) {
		try {
			GameRegistry.register(this);
			GameRegistry.register(itemblock.getConstructor(UBlock.class).newInstance(this));
		} catch (Exception ex) {
			UCoreConstants.LOGGER.error("Error catched while registering blocks.", ex);
		}
	}
	
}

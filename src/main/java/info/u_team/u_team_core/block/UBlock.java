package info.u_team.u_team_core.block;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.item.UItemBlock;
import info.u_team.u_team_core.sub.USub;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Block API<br>
 * -> Basic Block
 * 
 * @date 17.08.2017
 * @author MrTroble, HyCraftHD
 */

public class UBlock extends Block {
	
	private Class<? extends UItemBlock> uitemblock = null;
	
	public UBlock(String name, Material material) {
		this(name, material, null, UItemBlock.class);
	}
	
	public UBlock(String name, Material material, Class<? extends UItemBlock> itemblock) {
		this(name, material, null, itemblock);
	}
	
	public UBlock(String name, Material material, UCreativeTab tab) {
		this(name, material, tab, UItemBlock.class);
	}
	
	public UBlock(String name, Material material, UCreativeTab tab, Class<? extends UItemBlock> itemblock) {
		super(material);
		
		setRegistryName(USub.getID(), name);
		setUnlocalizedName(name);
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
		uitemblock = itemblock;
		
		register();
	}
	
	private final void register() {
		GameRegistry.registerBlock(this, uitemblock);
	}
	
	public Item getItem() {
		return Item.getItemFromBlock(this);
	}
	
}

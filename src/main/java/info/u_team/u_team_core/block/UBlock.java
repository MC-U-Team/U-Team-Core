package info.u_team.u_team_core.block;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.item.UItemBlock;
import info.u_team.u_team_core.sub.USub;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Block API<br>
 * -> Basic Block
 * 
 * @date 17.08.2017
 * @author MrTroble, HyCraftHD
 */

public class UBlock extends Block {
	
	private String name, modid;
	private Class<? extends UItemBlock> uitemblock = null;
	
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
		
		this.modid = USub.getID();
		this.name = name;
		
		setUnlocalizedName(name);
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
		uitemblock = itemblock;
		
		register();
	}
	
	private final void register() {
		GameRegistry.registerBlock(this, uitemblock, name);
	}
	
	public Item getItem() {
		return Item.getItemFromBlock(this);
	}
	
	public ResourceLocation getRegistryNameResourceLocation() {
		return new ResourceLocation(modid, name);
	}
	
}

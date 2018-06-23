package info.u_team.u_team_core.block;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.item.UItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.*;

/**
 * Block API<br>
 * -> Basic Block
 * 
 * @date 17.08.2017
 * @author MrTroble, HyCraftHD
 */

public class UBlock extends Block {
	
	protected String name;
	
	public UBlock(String name, Material material) {
		this(name, material, null);
	}
	
	public UBlock(String name, Material material, UCreativeTab tab) {
		super(material);
		
		this.name = name;
		
		if (tab != null) {
			setCreativeTab(tab);
		}
	}
	
	public Item getItem() {
		return Item.getItemFromBlock(this);
	}
	
	protected UItemBlock getItemBlock() {
		return new UItemBlock(this);
	}
	
	public void registerCommon(String modid) {
		ForgeRegistries.BLOCKS.register(this);
		ForgeRegistries.ITEMS.register(getItemBlock());
		if (FMLCommonHandler.instance().getSide().isClient()) {
			registerClient(modid);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void registerClient(String modid) {
	}
}

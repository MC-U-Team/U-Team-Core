package info.u_team.u_team_core.block;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.item.UItemBlock;
import info.u_team.u_team_core.model.IModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.*;

/**
 * Block API<br>
 * -> Basic Block
 * 
 * @date 17.08.2017
 * @author MrTroble, HyCraftHD
 */

public class UBlock extends Block implements IModelProvider {
	
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
	
	public UItemBlock getItemBlock() {
		return new UItemBlock(this);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		setModel(getItem(), 0, getRegistryName());
	}
	
}

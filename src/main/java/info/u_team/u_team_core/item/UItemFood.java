package info.u_team.u_team_core.item;

import info.u_team.u_team_core.api.IModelProvider;
import info.u_team.u_team_core.api.registry.IUItem;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.relauncher.*;

/**
 * Item API<br>
 * -> Basic Food Item
 * 
 * @date 27.12.17
 * @author HyCraftHD
 */

public class UItemFood extends ItemFood implements IUItem, IModelProvider {
	
	protected String name;
	
	public UItemFood(String name, int amount) {
		this(name, null, amount);
	}
	
	public UItemFood(String name, UCreativeTab tab, int amount) {
		this(name, tab, amount, 0.6F);
	}
	
	public UItemFood(String name, int amount, float saturation) {
		this(name, null, amount, saturation);
	}
	
	public UItemFood(String name, UCreativeTab tab, int amount, float saturation) {
		super(amount, saturation, false);
		
		this.name = name;
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		setModel(this, 0, getRegistryName());
	}
}

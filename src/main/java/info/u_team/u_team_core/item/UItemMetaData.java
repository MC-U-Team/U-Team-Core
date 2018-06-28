package info.u_team.u_team_core.item;

import info.u_team.u_team_core.api.IUMetaType;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.util.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * Item API<br>
 * -> Metadata Items
 * 
 * @date 26.06.2018
 * @author HyCraftHD
 *
 */
public class UItemMetaData extends UItem {
	
	private NonNullList<IUMetaType> list;
	
	public UItemMetaData(String name, NonNullList<IUMetaType> list) {
		this(name, null, list);
	}
	
	public UItemMetaData(String name, UCreativeTab tab, NonNullList<IUMetaType> list) {
		super(name, tab);
		this.list = list;
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getMetadata();
		if (MathUtil.isInRange(metadata, 0, list.size() - 1)) {
			return getUnlocalizedName() + "." + list.get(stack.getMetadata()).getName();
		}
		return getUnlocalizedName();
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab)) {
			for (int i = 0; i < list.size(); i++) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}
	
	@Override
	public void registerModel() {
		for (int i = 0; i < list.size(); i++) {
			setModel(this, i, new CustomResourceLocation(getRegistryName(), "/" + list.get(i).getName()));
		}
	}
	
}

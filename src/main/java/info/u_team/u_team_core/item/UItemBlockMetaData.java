package info.u_team.u_team_core.item;

import info.u_team.u_team_core.api.IMetaType;
import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_core.util.MathUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * ItemBlock API<br>
 * -> Metadata ItemBlock
 * 
 * @date 26.06.2018
 * @author HyCraftHD
 *
 */
public class UItemBlockMetaData extends UItemBlock {
	
	private IMetaType[] list;
	
	public UItemBlockMetaData(UBlock block, IMetaType[] list) {
		super(block);
		setHasSubtypes(true);
		this.list = list;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getMetadata();
		if (MathUtil.isInRange(metadata, 0, list.length - 1)) {
			return getUnlocalizedName() + "." + list[metadata].getName();
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
			for (int i = 0; i < list.length; i++) {
				items.add(new ItemStack(this, 1, list[i].getMetadata()));
			}
		}
	}
	
}

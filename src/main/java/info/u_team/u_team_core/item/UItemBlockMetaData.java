package info.u_team.u_team_core.item;

import java.util.List;

import info.u_team.u_team_core.api.IUMetaType;
import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_core.util.MathUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class UItemBlockMetaData extends UItemBlock {
	
	private List<IUMetaType> list;
	
	public UItemBlockMetaData(UBlock block, List<IUMetaType> list) {
		super(block);
		setHasSubtypes(true);
		this.list = list;
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
	
}

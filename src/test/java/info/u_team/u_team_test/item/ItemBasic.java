package info.u_team.u_team_test.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_test.init.TestItemGroups;
import net.minecraft.item.EnumRarity;

public class ItemBasic extends UItem {
	
	public ItemBasic(String name) {
		super(name, TestItemGroups.group, new Properties().rarity(EnumRarity.EPIC));
	}
	
}

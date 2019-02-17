package info.u_team.u_team_test.proxy;

import info.u_team.u_team_test.init.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;

public class CommonProxy {
	
	public static void construct() {
		TestItems.construct();
		TestBlocks.construct();
	}
	
	public static void setup() {
		TestItemGroups.setup();
		
		System.out.println("ROFLFLDFllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
		
		NonNullList<ItemStack> list = NonNullList.create();
		TestBlocks.basic.asItem().fillItemGroup(TestItemGroups.group, list);
		
		TestBlocks.basic.asItem().fillItemGroup(ItemGroup.SEARCH, list);
		
		System.out.println(list);
		
		NonNullList<ItemStack> list2 = NonNullList.create();
		Blocks.BEDROCK.asItem().fillItemGroup(TestItemGroups.group, list2);
		
		Blocks.BEDROCK.asItem().fillItemGroup(ItemGroup.SEARCH, list2);
		
		System.out.println(list2);
	}
	
}

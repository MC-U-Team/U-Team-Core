package info.u_team.u_team_core.util.stack;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;

/**
 * Item utility
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public class ItemUtil {
	
	public static Item from(Object obj) {
		Item item = null;
		if (obj instanceof ItemStack) {
			item = ((ItemStack) obj).getItem();
		} else if (obj instanceof Item) {
			item = (Item) obj;
		} else if (obj instanceof Block) {
			item = Item.getItemFromBlock((Block) obj);
		} else if (obj instanceof String || obj instanceof Integer) {
			String s;
			if (obj instanceof Integer) {
				s = Integer.valueOf((String) obj).toString();
			} else {
				s = (String) obj;
			}
			Block block = Block.getBlockFromName(s);
			item = Item.getByNameOrId(s);
			if (block != null) {
				item = Item.getItemFromBlock(block);
			}
		}
		return item;
	}
	
	public static ResourceLocation getRegistryName(Item item) {
		return item.getRegistryName();
	}
	
	public static String getModID(Item item) {
		return getRegistryName(item).getResourceDomain();
	}
	
	public static String getName(Item item) {
		return getRegistryName(item).getResourcePath();
	}
	
}

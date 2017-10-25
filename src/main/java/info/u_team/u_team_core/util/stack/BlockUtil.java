package info.u_team.u_team_core.util.stack;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;

/**
 * Block utility
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 *
 */
public class BlockUtil {
	
	public static Block from(Object obj) {
		Block block = null;
		if (obj instanceof ItemStack) {
			block = Block.getBlockFromItem(((ItemStack) obj).getItem());
		} else if (obj instanceof Item) {
			block = Block.getBlockFromItem((Item) obj);
		} else if (obj instanceof Block) {
			block = (Block) obj;
		} else if (obj instanceof String || obj instanceof Integer) {
			String s;
			if (obj instanceof Integer) {
				s = Integer.valueOf((String) obj).toString();
			} else {
				s = (String) obj;
			}
			block = Block.getBlockFromName(s);
		}
		return block;
	}
	
	public static ResourceLocation getRegistryName(Block block) {
		return (ResourceLocation) Block.blockRegistry.getNameForObject(block);
	}
	
	public static String getModID(Block block) {
		ResourceLocation loc = getRegistryName(block);
		return loc != null ? loc.getResourceDomain() : null;
	}
	
	public static String getName(Block block) {
		ResourceLocation loc = getRegistryName(block);
		return loc != null ? loc.getResourcePath() : null;
		
	}
	
}

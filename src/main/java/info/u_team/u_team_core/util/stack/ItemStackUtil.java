package info.u_team.u_team_core.util.stack;

import net.minecraft.item.*;

public class ItemStackUtil {
	
	public static ItemStack from(Object obj) {
		if (obj instanceof ItemStack) {
			return (ItemStack) obj;
		}
		Item item = ItemUtil.from(obj);
		return item != null ? new ItemStack(item) : null;
	}
}

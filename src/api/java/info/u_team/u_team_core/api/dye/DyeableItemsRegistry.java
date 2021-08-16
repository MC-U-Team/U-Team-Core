package info.u_team.u_team_core.api.dye;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.world.item.Item;

public class DyeableItemsRegistry {

	private static final Set<Item> DYEABLE_ITEMS = new HashSet<>();

	public static <T extends Item & IDyeableItem> void addItem(T item) {
		DYEABLE_ITEMS.add(item);
	}

	public static Set<Item> getDyeableItems() {
		return Collections.unmodifiableSet(DYEABLE_ITEMS);
	}

}

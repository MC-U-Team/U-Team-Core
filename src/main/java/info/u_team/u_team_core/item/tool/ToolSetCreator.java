package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IToolMaterial;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;

public class ToolSetCreator {
	
	public static ToolSet create(String name, Properties properties, IToolMaterial material) {
		return create(name, null, properties, material);
	}
	
	public static ToolSet create(String name, ItemGroup group, Properties properties, IToolMaterial material) {
		return new ToolSet(new UItemAxe(name + "_axe", group, properties, material), new UItemHoe(name + "_hoe", group, properties, material), new UItemPickaxe(name + "_pickaxe", group, properties, material), new UItemSpade(name + "_shovel", group, properties, material), new UItemSword(name + "_sword", group, properties, material));
	}
	
}

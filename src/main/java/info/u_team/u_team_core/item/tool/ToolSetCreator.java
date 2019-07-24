package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.util.ItemProperties;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;

public class ToolSetCreator {
	
	public static ToolSet create(String name, Properties properties, IToolMaterial material) {
		return create(name, null, properties, material);
	}
	
	public static ToolSet create(String name, ItemGroup group, Properties properties, IToolMaterial material) {
		return new ToolSet(new UAxeItem(name + "_axe", group, new ItemProperties(properties), material), new UHoeItem(name + "_hoe", group, new ItemProperties(properties), material), new UPickaxeItem(name + "_pickaxe", group, new ItemProperties(properties), material), new USpadeItem(name + "_shovel", group, new ItemProperties(properties), material), new USwordItem(name + "_sword", group, new ItemProperties(properties), material));
	}
	
}

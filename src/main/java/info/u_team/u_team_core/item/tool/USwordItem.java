package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.api.IToolMaterial.Tools;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;

public class USwordItem extends SwordItem {
	
	public USwordItem(Properties properties, IToolMaterial material) {
		this(null, properties, material);
	}
	
	public USwordItem(ItemGroup group, Properties properties, IToolMaterial material) {
		super(material, (int) material.getAdditionalDamage(Tools.SWORD), material.getAttackSpeed(Tools.SWORD), group == null ? properties : properties.group(group));
	}
}

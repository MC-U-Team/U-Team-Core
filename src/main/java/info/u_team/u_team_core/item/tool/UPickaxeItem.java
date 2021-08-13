package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.api.IToolMaterial.Tools;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.PickaxeItem;

public class UPickaxeItem extends PickaxeItem {
	
	public UPickaxeItem(Properties properties, IToolMaterial material) {
		this(null, properties, material);
	}
	
	public UPickaxeItem(CreativeModeTab group, Properties properties, IToolMaterial material) {
		super(material, (int) material.getAdditionalDamage(Tools.PICKAXE), material.getAttackSpeed(Tools.PICKAXE), group == null ? properties : properties.tab(group));
	}
}

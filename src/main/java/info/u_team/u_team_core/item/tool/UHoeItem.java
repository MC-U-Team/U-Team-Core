package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.api.IToolMaterial.Tools;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HoeItem;

public class UHoeItem extends HoeItem {

	public UHoeItem(Properties properties, IToolMaterial material) {
		this(null, properties, material);
	}

	public UHoeItem(CreativeModeTab group, Properties properties, IToolMaterial material) {
		super(material, (int) material.getAdditionalDamage(Tools.HOE), material.getAttackSpeed(Tools.HOE), group == null ? properties : properties.tab(group));
	}

}

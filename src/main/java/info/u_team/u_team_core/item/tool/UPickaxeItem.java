package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.api.IToolMaterial.Tools;
import net.minecraft.item.*;
import net.minecraftforge.common.ToolType;

public class UPickaxeItem extends PickaxeItem {
	
	public UPickaxeItem(Properties properties, IToolMaterial material) {
		this(null, properties, material);
	}
	
	public UPickaxeItem(ItemGroup group, Properties properties, IToolMaterial material) {
		super(material, (int) material.getAdditionalDamage(Tools.PICKAXE), material.getAttackSpeed(Tools.PICKAXE), applyToolType(group == null ? properties : properties.group(group), material));
	}
	
	private static Properties applyToolType(Properties properties, IToolMaterial material) {
		return properties.addToolType(ToolType.PICKAXE, material.getHarvestLevel());
	}
}
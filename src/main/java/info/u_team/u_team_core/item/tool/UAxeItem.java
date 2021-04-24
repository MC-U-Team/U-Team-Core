package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.api.IToolMaterial.Tools;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;

public class UAxeItem extends AxeItem {
	
	public UAxeItem(Properties properties, IToolMaterial material) {
		this(null, properties, material);
	}
	
	public UAxeItem(ItemGroup group, Properties properties, IToolMaterial material) {
		super(material, material.getAdditionalDamage(Tools.AXE), material.getAttackSpeed(Tools.AXE), applyToolType(group == null ? properties : properties.group(group), material));
	}
	
	private static Properties applyToolType(Properties properties, IToolMaterial material) {
		return properties.addToolType(ToolType.AXE, material.getHarvestLevel());
	}
}

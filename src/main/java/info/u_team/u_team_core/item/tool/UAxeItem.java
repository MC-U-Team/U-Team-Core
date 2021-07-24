package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.api.IToolMaterial.Tools;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.ToolType;

import net.minecraft.world.item.Item.Properties;

public class UAxeItem extends AxeItem {
	
	public UAxeItem(Properties properties, IToolMaterial material) {
		this(null, properties, material);
	}
	
	public UAxeItem(CreativeModeTab group, Properties properties, IToolMaterial material) {
		super(material, material.getAdditionalDamage(Tools.AXE), material.getAttackSpeed(Tools.AXE), applyToolType(group == null ? properties : properties.tab(group), material));
	}
	
	private static Properties applyToolType(Properties properties, IToolMaterial material) {
		return properties.addToolType(ToolType.AXE, material.getLevel());
	}
}

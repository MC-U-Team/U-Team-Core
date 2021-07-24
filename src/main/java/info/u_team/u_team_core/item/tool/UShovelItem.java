package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.api.IToolMaterial.Tools;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ShovelItem;
import net.minecraftforge.common.ToolType;

public class UShovelItem extends ShovelItem {
	
	public UShovelItem(Properties properties, IToolMaterial material) {
		this(null, properties, material);
	}
	
	public UShovelItem(CreativeModeTab group, Properties properties, IToolMaterial material) {
		super(material, material.getAdditionalDamage(Tools.SHOVEL), material.getAttackSpeed(Tools.SHOVEL), applyToolType(group == null ? properties : properties.tab(group), material));
	}
	
	private static Properties applyToolType(Properties properties, IToolMaterial material) {
		return properties.addToolType(ToolType.SHOVEL, material.getLevel());
	}
	
}

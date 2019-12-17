package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.api.IToolMaterial.Tools;
import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.item.*;
import net.minecraftforge.common.ToolType;

public class UAxeItem extends AxeItem implements IURegistryType {
	
	protected final String name;
	
	public UAxeItem(String name, Properties properties, IToolMaterial material) {
		this(name, null, properties, material);
	}
	
	public UAxeItem(String name, ItemGroup group, Properties properties, IToolMaterial material) {
		super(material, material.getAdditionalDamage(Tools.AXE), material.getAttackSpeed(Tools.AXE), applyToolType(group == null ? properties : properties.group(group), material));
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	private static Properties applyToolType(Properties properties, IToolMaterial material) {
		return properties.addToolType(ToolType.AXE, material.getHarvestLevel());
	}
}

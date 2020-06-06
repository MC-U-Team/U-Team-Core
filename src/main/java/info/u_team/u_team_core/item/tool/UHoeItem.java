package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.api.IToolMaterial.Tools;
import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.item.*;

public class UHoeItem extends HoeItem implements IURegistryType {
	
	protected final String name;
	
	public UHoeItem(String name, Properties properties, IToolMaterial material) {
		this(name, null, properties, material);
	}
	
	public UHoeItem(String name, ItemGroup group, Properties properties, IToolMaterial material) {
		super(material, material.getAttackSpeed(Tools.HOE), group == null ? properties : properties.group(group));
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

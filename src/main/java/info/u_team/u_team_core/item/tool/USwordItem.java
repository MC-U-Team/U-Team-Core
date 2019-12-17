package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.api.IToolMaterial.Tools;
import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.item.*;

public class USwordItem extends SwordItem implements IURegistryType {
	
	protected final String name;
	
	public USwordItem(String name, Properties properties, IToolMaterial material) {
		this(name, null, properties, material);
	}
	
	public USwordItem(String name, ItemGroup group, Properties properties, IToolMaterial material) {
		super(material, (int) material.getAdditionalDamage(Tools.SWORD), material.getAttackSpeed(Tools.SWORD), group == null ? properties : properties.group(group));
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

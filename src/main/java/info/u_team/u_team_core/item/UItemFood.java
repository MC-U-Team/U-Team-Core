package info.u_team.u_team_core.item;

import info.u_team.u_team_core.api.registry.IUItem;
import net.minecraft.item.*;

public class UItemFood extends ItemFood implements IUItem {
	
	protected final String name;
	
	public UItemFood(String name, Properties properties, int healAmount, float saturationModifier, boolean meat) {
		this(name, null, properties, healAmount, saturationModifier, meat);
	}
	
	public UItemFood(String name, ItemGroup group, Properties properties, int healAmount, float saturationModifier, boolean meat) {
		super(healAmount, saturationModifier, meat, group == null ? properties : properties.group(group));
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

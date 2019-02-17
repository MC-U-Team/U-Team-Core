package info.u_team.u_team_core.item;

import info.u_team.u_team_core.api.registry.IUItem;
import net.minecraft.item.Item;

public class UItem extends Item implements IUItem {
	
	protected final String name;
	
	public UItem(String name, Properties properties) {
		super(properties);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
}

package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.api.item.ExtendedTier.Tools;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ShovelItem;

public class UShovelItem extends ShovelItem {
	
	public UShovelItem(Properties properties, ExtendedTier tier) {
		this(null, properties, tier);
	}
	
	public UShovelItem(CreativeModeTab creativeTab, Properties properties, ExtendedTier tier) {
		super(tier, tier.getAttackDamage(Tools.SHOVEL), tier.getAttackSpeed(Tools.SHOVEL), creativeTab == null ? properties : properties.tab(creativeTab));
	}
	
}

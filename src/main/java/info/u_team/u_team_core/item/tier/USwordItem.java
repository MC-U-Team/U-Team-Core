package info.u_team.u_team_core.item.tier;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.api.item.ExtendedTier.Tools;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.SwordItem;

public class USwordItem extends SwordItem {
	
	public USwordItem(Properties properties, ExtendedTier tier) {
		this(null, properties, tier);
	}
	
	public USwordItem(CreativeModeTab creativeTab, Properties properties, ExtendedTier tier) {
		super(tier, (int) tier.getAttackDamage(Tools.SWORD), tier.getAttackSpeed(Tools.SWORD), creativeTab == null ? properties : properties.tab(creativeTab));
	}
}

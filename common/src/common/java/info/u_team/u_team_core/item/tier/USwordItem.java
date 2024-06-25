package info.u_team.u_team_core.item.tier;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.api.item.ExtendedTier.Tools;
import net.minecraft.world.item.SwordItem;

public class USwordItem extends SwordItem {
	
	public USwordItem(Properties properties, ExtendedTier tier) {
		super(tier, properties.attributes(createAttributes(tier, (int) tier.getAttackDamage(Tools.SWORD), tier.getAttackSpeed(Tools.SWORD))));
	}
}

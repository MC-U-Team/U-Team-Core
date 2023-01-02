package info.u_team.u_team_core.item.tier;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.api.item.ExtendedTier.Tools;
import net.minecraft.world.item.AxeItem;

public class UAxeItem extends AxeItem {
	
	public UAxeItem(Properties properties, ExtendedTier tier) {
		super(tier, tier.getAttackDamage(Tools.AXE), tier.getAttackSpeed(Tools.AXE), properties);
	}
}

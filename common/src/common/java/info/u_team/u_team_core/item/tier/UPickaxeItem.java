package info.u_team.u_team_core.item.tier;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.api.item.ExtendedTier.Tools;
import net.minecraft.world.item.PickaxeItem;

public class UPickaxeItem extends PickaxeItem {
	
	public UPickaxeItem(Properties properties, ExtendedTier tier) {
		super(tier, properties.attributes(createAttributes(tier, tier.getAttackDamage(Tools.PICKAXE), tier.getAttackSpeed(Tools.PICKAXE))));
	}
}

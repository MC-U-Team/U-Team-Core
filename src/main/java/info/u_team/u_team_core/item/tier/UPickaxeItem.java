package info.u_team.u_team_core.item.tier;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.api.item.ExtendedTier.Tools;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.PickaxeItem;

public class UPickaxeItem extends PickaxeItem {
	
	public UPickaxeItem(Properties properties, ExtendedTier tier) {
		this(null, properties, tier);
	}
	
	public UPickaxeItem(CreativeModeTab creativeTab, Properties properties, ExtendedTier tier) {
		super(tier, (int) tier.getAttackDamage(Tools.PICKAXE), tier.getAttackSpeed(Tools.PICKAXE), creativeTab == null ? properties : properties.tab(creativeTab));
	}
}

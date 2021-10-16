package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.api.item.ExtendedTier.Tools;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;

public class UAxeItem extends AxeItem {
	
	public UAxeItem(Properties properties, ExtendedTier tier) {
		this(null, properties, tier);
	}
	
	public UAxeItem(CreativeModeTab creativeTab, Properties properties, ExtendedTier tier) {
		super(tier, tier.getAttackDamage(Tools.AXE), tier.getAttackSpeed(Tools.AXE), creativeTab == null ? properties : properties.tab(creativeTab));
	}
}

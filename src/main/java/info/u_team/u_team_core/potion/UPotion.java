package info.u_team.u_team_core.potion;

import info.u_team.u_team_core.api.registry.IUPotion;
import net.minecraft.potion.Potion;

public class UPotion extends Potion implements IUPotion {
	
	protected final String name;
	
	protected UPotion(String name, boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

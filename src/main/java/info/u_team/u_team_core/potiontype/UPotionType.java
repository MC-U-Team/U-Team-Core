package info.u_team.u_team_core.potiontype;

import info.u_team.u_team_core.api.registry.IUPotion;
import net.minecraft.potion.*;

public class UPotionType extends PotionType implements IUPotion {
	
	protected final String name;
	
	public UPotionType(String name, PotionEffect... effects) {
		super(effects);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

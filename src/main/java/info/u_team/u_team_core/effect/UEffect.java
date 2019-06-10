package info.u_team.u_team_core.effect;

import info.u_team.u_team_core.api.registry.IUPotion;
import net.minecraft.potion.*;

public class UEffect extends Effect implements IUPotion {
	
	protected final String name;
	
	public UEffect(String name, EffectType type, int liquidColor) {
		super(type, liquidColor);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

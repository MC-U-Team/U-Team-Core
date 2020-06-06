package info.u_team.u_team_core.potion;

import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.potion.*;

public class UPotion extends Potion implements IURegistryType {
	
	protected final String name;
	
	public UPotion(String name, EffectInstance... effects) {
		this(name, null, effects);
	}
	
	public UPotion(String name, String unlocalizedPrefix, EffectInstance... effects) {
		super(unlocalizedPrefix, effects);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

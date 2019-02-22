package info.u_team.u_team_core.sound;

import info.u_team.u_team_core.api.registry.IUSoundEvent;
import net.minecraft.util.*;

public class USoundEvent extends SoundEvent implements IUSoundEvent {
	
	protected final String name;
	
	public USoundEvent(ResourceLocation resource) {
		super(resource);
		name = resource.getPath();
	}
	
	public USoundEvent(String name, ResourceLocation resource) {
		super(resource);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}

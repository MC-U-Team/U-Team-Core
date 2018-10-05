package info.u_team.u_team_core.sound;

import info.u_team.u_team_core.api.registry.IUSoundEvent;
import net.minecraft.util.*;

/**
 * Sound API<br>
 * -> Basic Soundevent
 * 
 * @date 05.10.2018
 * @author HyCraftHD
 */
public class USoundEvent extends SoundEvent implements IUSoundEvent {
	
	protected String name;
	
	public USoundEvent(ResourceLocation resource) {
		super(resource);
		name = resource.getNamespace();
	}
	
	public USoundEvent(String name, ResourceLocation resource) {
		super(resource);
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}

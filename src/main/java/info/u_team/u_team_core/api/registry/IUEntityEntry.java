package info.u_team.u_team_core.api.registry;

import net.minecraftforge.fml.common.registry.EntityEntry;

/**
 * Mark a entityentry as uentityentry for registry things
 * 
 * @author HyCraftHD
 * @date 05.07.2018
 */
public interface IUEntityEntry extends IURegistry {
	
	public EntityEntry getEntry();
	
}

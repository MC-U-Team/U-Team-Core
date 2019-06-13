package info.u_team.u_team_core.api.registry;

import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Implement this interface in {@link IForgeRegistryEntry} classes. Then the {@link BaseRegistryUtil} can retrieve the
 * name and set the registry name.
 */
public interface IURegistryType {
	
	/**
	 * Must return a unique name per {@link IForgeRegistryEntry}
	 * 
	 * @return Entry name
	 */
	String getEntryName();
}

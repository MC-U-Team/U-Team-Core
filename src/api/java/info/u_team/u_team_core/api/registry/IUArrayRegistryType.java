package info.u_team.u_team_core.api.registry;

import info.u_team.u_team_core.util.registry.BaseRegistryUtil;

/**
 * Implement this in your class which holds entries so the {@link BaseRegistryUtil} can register them all.
 */
@Deprecated
public interface IUArrayRegistryType<T> {
	
	/**
	 * Must always returns the same registry type array copy. Because of mutability this array must be copied.
	 * 
	 * @return Array of entries
	 */
	T[] getArray();
	
}

package info.u_team.u_team_core.api.registry;

import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import net.minecraftforge.registries.*;

/**
 * Implement this in your class which holds {@link IForgeRegistryEntry} entries so the {@link BaseRegistryUtil} can
 * register them all.
 */
public interface IUArrayRegistryType<T extends IForgeRegistryEntry<T>> {
	
	/**
	 * Must always returns the same registry type array copy. Because of mutability this array must be copied.
	 * 
	 * @return Array of {@link IForgeRegistryEntry}
	 */
	T[] getArray();
	
}

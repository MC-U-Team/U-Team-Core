package info.u_team.u_team_core.intern.coremod;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpw.mods.modlauncher.api.ITransformer;
import info.u_team.u_team_core.intern.coremod.transformer.DataProviderClassTransformer;
import net.neoforged.neoforgespi.coremod.ICoreMod;

public class UCoreNeoForgeCoreMod implements ICoreMod {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UCoreNeoForgeCoreMod.class);
	
	public UCoreNeoForgeCoreMod() {
		LOGGER.debug("Loading uteamcore coremod");
	}

	@Override
	public Iterable<? extends ITransformer<?>> getTransformers() {
		return Set.of(new DataProviderClassTransformer());
	}
	
}

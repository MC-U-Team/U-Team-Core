package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.construct.*;
import info.u_team.u_team_core.intern.config.ClientConfig;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

@Construct(modid = UCoreMod.MODID)
public class UCoreCommonConstruct implements IModConstruct {
	
	@Override
	public void construct() {
		ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.CONFIG);
		
		BusRegister.registerMod(UCoreNetwork::registerMod);
		BusRegister.registerMod(UCoreRecipeSerializers::registerMod);
		BusRegister.registerMod(UCoreLazySpawnEggs::registerMod);
		BusRegister.registerMod(UCoreLootFunctions::registerMod);
		
		BusRegister.registerForge(UCoreCommands::registerForge);
	}
}
package info.u_team.u_team_core.intern.init;

import java.util.Objects;

import org.apache.commons.lang3.tuple.Pair;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.IModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;

@Construct(modid = UCoreMod.MODID)
public class UCoreCommonConstruct implements IModConstruct {
	
	@Override
	public void construct() {
		final String version = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> version, (remoteVersion, network) -> {
			if (remoteVersion == null) {
				return true;
			}
			return Objects.equals(remoteVersion, version);
		}));
		
		BusRegister.registerMod(UCoreNetwork::registerMod);
		BusRegister.registerMod(UCoreRecipeSerializers::registerMod);
		BusRegister.registerMod(UCoreLazySpawnEggs::registerMod);
		BusRegister.registerMod(UCoreLootFunctions::registerMod);
		
		BusRegister.registerForge(UCoreCommands::registerForge);
	}
}

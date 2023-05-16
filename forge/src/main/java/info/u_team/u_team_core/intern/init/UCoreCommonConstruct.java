package info.u_team.u_team_core.intern.init;

import java.util.Objects;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraftforge.fml.IExtensionPoint.DisplayTest;
import net.minecraftforge.fml.ModLoadingContext;

@Construct(modid = UCoreMod.MODID)
public class UCoreCommonConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		final String version = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();
		ModLoadingContext.get().registerExtensionPoint(DisplayTest.class, () -> new DisplayTest(() -> version, (remoteVersion, network) -> {
			if (remoteVersion == null) {
				return true;
			}
			return Objects.equals(remoteVersion, version);
		}));
		
		BusRegister.registerMod(UCoreNetwork::registerMod);
		BusRegister.registerMod(UCoreRecipeSerializers::registerMod);
		UCoreLootFunctions.register();
		
		BusRegister.registerForge(UCoreCommands::registerForge);
	}
}

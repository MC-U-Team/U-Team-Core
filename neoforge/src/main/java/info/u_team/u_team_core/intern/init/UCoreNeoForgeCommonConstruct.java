package info.u_team.u_team_core.intern.init;

import java.util.Objects;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.neoforged.fml.IExtensionPoint.DisplayTest;
import net.neoforged.fml.ModLoadingContext;

@Construct(modid = UCoreMod.MODID, priority = 2000)
public class UCoreNeoForgeCommonConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		final String version = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();
		ModLoadingContext.get().registerExtensionPoint(DisplayTest.class, () -> new DisplayTest(() -> version, (remoteVersion, network) -> {
			if (remoteVersion == null) {
				return true;
			}
			return Objects.equals(remoteVersion, version);
		}));
		
		UCoreNetwork.NETWORK.setProtocolAcceptor(protocolVersion -> {
			// Allow clients to join / view servers without errors when uteamcore is not present there
			// return NetworkRegistry.acceptMissingOr(UCoreNetwork.NETWORK.getProtocolVersion()).test(protocolVersion);
			return true; // TODO fixup
		}, received -> received == UCoreNetwork.NETWORK.getProtocolVersion());
		
		BusRegister.registerMod(UCoreNetworkNeoForge::registerMod);
		BusRegister.registerMod(UCoreNeoForgeCapabilities::registerMod);
	}
}

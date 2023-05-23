package info.u_team.u_team_core.intern.init;

import java.util.Objects;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.impl.ForgeNetworkHandler;
import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraftforge.fml.IExtensionPoint.DisplayTest;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;

@Construct(modid = UCoreMod.MODID, priority = 2000)
public class UCoreForgeCommonConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		final String version = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();
		ModLoadingContext.get().registerExtensionPoint(DisplayTest.class, () -> new DisplayTest(() -> version, (remoteVersion, network) -> {
			if (remoteVersion == null) {
				return true;
			}
			return Objects.equals(remoteVersion, version);
		}));
		
		BusRegister.registerMod(UCoreNetworkForge::registerMod);
		BusRegister.registerMod(UCoreRecipeSerializers::registerMod);
		
		CastUtil.assertCast(UCoreNetwork.NETWORK, ForgeNetworkHandler.class).setProtocolVersion(() -> version, protocolVersion -> {
			// Allow clients to join / view servers without errors when uteamcore is not present there
			return NetworkRegistry.acceptMissingOr(version).test(protocolVersion);
		}, version::equals);
	}
}

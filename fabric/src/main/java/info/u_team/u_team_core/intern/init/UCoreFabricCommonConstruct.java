package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.intern.update.UpdateResolver;
import info.u_team.u_team_core.item.UFabricSpawnEggItem;

@Construct(modid = UCoreMod.MODID, priority = 2000)
public class UCoreFabricCommonConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		UpdateResolver.load();
		
		UFabricSpawnEggItem.Handler.register();
	}
	
}

package info.u_team.u_team_core.intern.data.provider;

import info.u_team.u_team_core.data.CommonBlockStateProvider;
import info.u_team.u_team_core.data.GenerationData;

public class UCoreBlockStatesProvider extends CommonBlockStateProvider {
	
	public UCoreBlockStatesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		models().withExistingParent("facing", "cube") //
				.texture("north", "#front") //
				.texture("east", "#side") //
				.texture("south", "#side") //
				.texture("west", "#side") //
				.texture("up", "#side") //
				.texture("down", "#side");
		
		models().withExistingParent("facing_bottom_top", "cube") //
				.texture("north", "#front") //
				.texture("east", "#side") //
				.texture("south", "#side") //
				.texture("west", "#side") //
				.texture("up", "#top") //
				.texture("down", "#bottom");
	}
	
}

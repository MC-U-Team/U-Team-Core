package info.u_team.u_team_core.intern.data.provider;

import info.u_team.u_team_core.data.*;

public class UCoreBlockStatesProvider extends CommonBlockStatesProvider {
	
	public UCoreBlockStatesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerStatesAndModels() {
		withExistingParent("facing", "cube") //
				.texture("north", "#front") //
				.texture("east", "#side") //
				.texture("south", "#side") //
				.texture("west", "#side") //
				.texture("up", "#side") //
				.texture("down", "#side");
		
		withExistingParent("facing_bottom_top", "cube") //
				.texture("north", "#front") //
				.texture("east", "#side") //
				.texture("south", "#side") //
				.texture("west", "#side") //
				.texture("up", "#top") //
				.texture("down", "#bottom");
	}
	
}

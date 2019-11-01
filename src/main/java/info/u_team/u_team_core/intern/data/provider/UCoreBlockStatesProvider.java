package info.u_team.u_team_core.intern.data.provider;

import info.u_team.u_team_core.data.CommonBlockStatesProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class UCoreBlockStatesProvider extends CommonBlockStatesProvider {
	
	public UCoreBlockStatesProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
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

package info.u_team.u_team_core.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.*;

public class CommonItemModelsProvider extends ItemModelProvider {
	
	public CommonItemModelsProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}
	
	@Override
	public String getName() {
		return "Item-Models";
	}
	
	@Override
	protected void registerModels() {
	}
	
}

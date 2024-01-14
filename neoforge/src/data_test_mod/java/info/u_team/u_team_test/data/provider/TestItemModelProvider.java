package info.u_team.u_team_test.data.provider;

import com.google.gson.JsonObject;

import info.u_team.u_team_core.data.CommonItemModelProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.init.TestBlocks;
import info.u_team.u_team_test.init.TestFluids;
import info.u_team.u_team_test.init.TestItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;

public class TestItemModelProvider extends CommonItemModelProvider {
	
	public TestItemModelProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		// Items
		iterateItems(TestItems.BASIC_TOOL, this::simpleHandheld);
		iterateItems(TestItems.BASIC_ARMOR, this::simpleGenerated);
		// Blocks
		simpleBlock(TestBlocks.BASIC.get());
		simpleBlock(TestBlocks.BASIC_ENERGY_CREATOR.get());
		simpleBlock(TestBlocks.BASIC_FLUID_INVENTORY.get());
		
		final ResourceLocation bucketModel = modLoc("item/" + getPath(TestItems.TEST_FLUID_BUCKET.get()));
		existingFileHelper.trackGenerated(bucketModel, MODEL);
		generatedModels.computeIfAbsent(bucketModel, location -> new ItemModelBuilder(location, existingFileHelper) {
			
			@Override
			public JsonObject toJson() {
				final JsonObject root = new JsonObject();
				root.addProperty("parent", "neoforge:item/bucket_drip");
				root.addProperty("loader", "neoforge:fluid_container");
				root.addProperty("fluid", TestFluids.TEST_FLUID.getId().toString());
				return root;
			};
		});
	}
	
}

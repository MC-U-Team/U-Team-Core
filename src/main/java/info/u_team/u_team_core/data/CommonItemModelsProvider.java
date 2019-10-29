package info.u_team.u_team_core.data;

import java.io.IOException;

import org.apache.logging.log4j.*;

import net.minecraft.data.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;

public abstract class CommonItemModelsProvider extends ItemModelProvider {
	
	protected final Marker marker;
	
	public CommonItemModelsProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
		marker = MarkerManager.getMarker(getName());
	}
	
	@Override
	public void act(DirectoryCache cache) throws IOException {
		this.cache = cache;
		generatedModels.clear();
		registerModels();
		generatedModels.values().forEach(model -> {
			try {
				final ResourceLocation location = model.getLocation();
				CommonProvider.write(cache, model.toJson(), generator.getOutputFolder().resolve("assets/" + location.getNamespace() + "/models/" + location.getPath() + ".json"));
			} catch (IOException ex) {
				CommonProvider.LOGGER.error(marker, "Could not write data.", ex);
			}
		});
		this.cache = null;
	}
	
	@Override
	public String getName() {
		return "Item-Models";
	}
}

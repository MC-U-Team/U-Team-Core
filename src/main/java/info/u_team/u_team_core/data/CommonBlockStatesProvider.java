package info.u_team.u_team_core.data;

import java.io.IOException;

import org.apache.logging.log4j.*;

import com.google.common.base.Preconditions;

import net.minecraft.data.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;

public abstract class CommonBlockStatesProvider extends BlockStateProvider {
	
	protected final Marker marker;
	
	public CommonBlockStatesProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
		marker = MarkerManager.getMarker(getName());
	}
	
	@Override
	public void act(DirectoryCache cache) throws IOException {
		this.cache = cache;
		generatedModels.clear();
		registerModels0();
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
	
	// We need to overide registerModels, but this method is marked final...
	private void registerModels0() {
		registeredBlocks.clear();
		registerStatesAndModels();
		
		registeredBlocks.forEach((block, generatedState) -> {
			try {
				final ResourceLocation location = Preconditions.checkNotNull(block.getRegistryName());
				CommonProvider.write(cache, generatedState.toJson(), generator.getOutputFolder().resolve("assets/" + location.getNamespace() + "/blockstates/" + location.getPath() + ".json"));
			} catch (IOException ex) {
				CommonProvider.LOGGER.error(marker, "Could not write data.", ex);
			}
		});
	}
	
	@Override
	public String getName() {
		return "Block-States | Block-Models";
	}
}

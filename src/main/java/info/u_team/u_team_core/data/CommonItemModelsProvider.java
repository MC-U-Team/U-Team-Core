package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.apache.logging.log4j.*;

import info.u_team.u_team_core.api.registry.IUArrayRegistryType;
import net.minecraft.block.Block;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.client.model.generators.ModelFile.*;

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
	
	// Item model methods
	
	protected void simpleGenerated(IItemProvider provider) {
		simpleParent(provider, "item/generated");
	}
	
	protected void simpleHandheld(IItemProvider provider) {
		simpleParent(provider, "item/handheld");
	}
	
	protected void simpleParent(IItemProvider provider, final String parent) {
		final String registryPath = provider.asItem().getRegistryName().getPath();
		getBuilder(registryPath).parent(new UncheckedModelFile(parent)).texture("layer0", "item/" + registryPath);
	}
	
	protected void simpleBlock(Block block) {
		final ResourceLocation registryName = block.getRegistryName();
		getBuilder(registryName.getPath()).parent(new UncheckedModelFile(new ResourceLocation(registryName.getNamespace(), "block/" + registryName.getPath())));
	}
	
	// Utility methods
	protected void iterateItems(IUArrayRegistryType<Item> type, Consumer<IItemProvider> item) {
		Stream.of(type.getArray()).forEach(item);
	}
	
	protected void iterateBlocks(IUArrayRegistryType<Block> type, Consumer<IItemProvider> item) {
		Stream.of(type.getArray()).forEach(item);
	}
	
	protected String getPath(IItemProvider provider) {
		return provider.asItem().getRegistryName().getPath();
	}
	
}

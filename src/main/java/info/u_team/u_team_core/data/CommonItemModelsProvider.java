package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.Consumer;

import org.apache.logging.log4j.*;

import net.minecraft.block.Block;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;

public abstract class CommonItemModelsProvider extends ItemModelProvider {
	
	protected final Marker marker;
	
	protected final GenerationData data;
	protected final String modid;
	protected final DataGenerator generator;
	
	public CommonItemModelsProvider(GenerationData data) {
		super(data.getGenerator(), data.getModid(), data.getExistingFileHelper());
		this.data = data;
		this.modid = data.getModid();
		this.generator = data.getGenerator();
		marker = MarkerManager.getMarker(getName());
	}
	
	@Override
	public void act(DirectoryCache cache) throws IOException {
		generatedModels.clear();
		registerModels();
		generatedModels.values().forEach(model -> {
			try {
				final ResourceLocation location = model.getLocation();
				CommonProvider.write(cache, model.toJson(), generator.getOutputFolder().resolve("assets/" + location.getNamespace() + "/models/" + location.getPath() + ".json"));
			} catch (final IOException ex) {
				CommonProvider.LOGGER.error(marker, "Could not write data.", ex);
			}
		});
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
	protected void iterateItems(Iterator<? extends Item> iterator, Consumer<IItemProvider> item) {
		iterator.forEachRemaining(item);
	}
	
	protected void iterateBlocks(Iterator<? extends Block> iterator, Consumer<IItemProvider> item) {
		iterator.forEachRemaining(item);
	}
	
	protected String getPath(IItemProvider provider) {
		return provider.asItem().getRegistryName().getPath();
	}
	
}

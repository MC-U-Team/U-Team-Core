package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.function.Function;

import org.apache.logging.log4j.*;

import com.google.common.base.Preconditions;

import info.u_team.u_team_core.UCoreMain;
import net.minecraft.block.*;
import net.minecraft.data.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;

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
	
	// Block state methods
	protected void facingBlock(Block block, ModelFile modelFile) {
		facingBlock(block, modelFile, 0);
	}
	
	protected void facingBlock(Block block, ModelFile modelFile, int angleOffset) {
		facingBlock(block, $ -> modelFile, angleOffset);
	}
	
	protected void facingBlock(Block block, Function<BlockState, ModelFile> modelFunc, int angleOffset) {
		getVariantBuilder(block).forAllStates(state -> {
			final Direction direction = state.get(BlockStateProperties.FACING);
			return ConfiguredModel.builder() //
					.modelFile(modelFunc.apply(state)) //
					.rotationX(direction == Direction.DOWN ? 90 : direction == Direction.UP ? 270 : 0) //
					.rotationY(direction.getAxis().isVertical() ? 0 : (((int) direction.getHorizontalAngle()) + angleOffset + 180) % 360) //
					.build(); //
		});
	}
	
	// Block model methods
	protected BlockModelBuilder cubeFacing(String name, ResourceLocation front, ResourceLocation side) {
		return getBuilder(name) //
				.parent(new UncheckedModelFile(new ResourceLocation(UCoreMain.MODID, "block/facing"))) //
				.texture("front", front) //
				.texture("side", side);
	}
	
	protected BlockModelBuilder cubeFacingBottomTop(String name, ResourceLocation front, ResourceLocation bottom, ResourceLocation top, ResourceLocation side) {
		return getBuilder(name) //
				.parent(new UncheckedModelFile(new ResourceLocation(UCoreMain.MODID, "block/facing_bottom_top"))) //
				.texture("front", front) //
				.texture("bottom", bottom) //
				.texture("top", top) //
				.texture("side", side);
	}
	
	// Utility methods
	protected String getPath(Block block) {
		return block.getRegistryName().getPath();
	}
}

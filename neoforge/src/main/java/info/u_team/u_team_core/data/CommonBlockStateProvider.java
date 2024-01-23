package info.u_team.u_team_core.data;

import java.util.function.Function;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile;

public abstract class CommonBlockStateProvider extends BlockStateProvider implements CommonDataProvider.NoParam {
	
	private final GenerationData generationData;
	
	public CommonBlockStateProvider(GenerationData generationData) {
		super(generationData.output(), generationData.modid(), generationData.existingFileHelper());
		this.generationData = generationData;
	}
	
	@Override
	public GenerationData getGenerationData() {
		return generationData;
	}
	
	@Override
	protected final void registerStatesAndModels() {
		register(null);
	}
	
	@Override
	public String getName() {
		return "Block-State: " + nameSuffix();
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
			final Direction direction = state.getValue(BlockStateProperties.FACING);
			return ConfiguredModel.builder() //
					.modelFile(modelFunc.apply(state)) //
					.rotationX(direction == Direction.DOWN ? 90 : direction == Direction.UP ? 270 : 0) //
					.rotationY(direction.getAxis().isVertical() ? 0 : (((int) direction.toYRot()) + angleOffset + 180) % 360) //
					.build(); //
		});
	}
	
	// Block model methods
	protected BlockModelBuilder cubeFacing(String name, ResourceLocation front, ResourceLocation side) {
		return cubeFacing(name, front, side, side);
	}
	
	protected BlockModelBuilder cubeFacing(String name, ResourceLocation front, ResourceLocation side, ResourceLocation particle) {
		return models().getBuilder(name) //
				.parent(new UncheckedModelFile(new ResourceLocation(UCoreMod.MODID, "block/facing"))) //
				.texture("particle", particle) //
				.texture("front", front) //
				.texture("side", side);
	}
	
	protected BlockModelBuilder cubeFacingBottomTop(String name, ResourceLocation front, ResourceLocation bottom, ResourceLocation top, ResourceLocation side) {
		return cubeFacingBottomTop(name, front, bottom, top, side, side);
	}
	
	protected BlockModelBuilder cubeFacingBottomTop(String name, ResourceLocation front, ResourceLocation bottom, ResourceLocation top, ResourceLocation side, ResourceLocation particle) {
		return models().getBuilder(name) //
				.parent(new UncheckedModelFile(new ResourceLocation(UCoreMod.MODID, "block/facing_bottom_top"))) //
				.texture("particle", particle) //
				.texture("front", front) //
				.texture("bottom", bottom) //
				.texture("top", top) //
				.texture("side", side);
	}
	
	// Utility methods
	protected String getPath(Block block) {
		return RegistryUtil.getBuiltInRegistry(Registries.BLOCK).getKey(block).getPath();
	}
	
}

package info.u_team.u_team_core.util;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

/**
 * Utility methods for interaction with {@link ModelBakery} private methods.
 *
 * @author HyCraftHD
 */
public class ModelUtil {
	
	static {
		synchronized (BlockStateModelLoader.STATIC_DEFINITIONS) {
			if (!(BlockStateModelLoader.STATIC_DEFINITIONS instanceof HashMap)) {
				final Map<ResourceLocation, StateDefinition<Block, BlockState>> mutableMap = new HashMap<>();
				BlockStateModelLoader.STATIC_DEFINITIONS.forEach(mutableMap::put);
				BlockStateModelLoader.STATIC_DEFINITIONS = mutableMap;
			}
		}
	}
	
	/**
	 * Replace the default state definition with a custom one. Can remove or add states for the model
	 *
	 * @param location Resource location of the model
	 * @param definition Custom state definition
	 */
	public static void addCustomStateContainer(ResourceLocation location, StateDefinition<Block, BlockState> definition) {
		BlockStateModelLoader.STATIC_DEFINITIONS.put(location, definition);
	}
	
	/**
	 * Utility class for an empty state definition
	 *
	 * @author HyCraftHD
	 */
	public static class EmptyStateDefinition extends StateDefinition<Block, BlockState> {
		
		public EmptyStateDefinition(Block block) {
			super(Block::defaultBlockState, block, BlockState::new, new HashMap<>());
		}
		
		@Override
		public ImmutableList<BlockState> getPossibleStates() {
			return getOwner().getStateDefinition().getPossibleStates();
		}
	}
}

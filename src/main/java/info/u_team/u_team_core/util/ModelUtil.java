package info.u_team.u_team_core.util;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.client.event.ModelRegistryEvent;

/**
 * Utility methods for interaction with {@link ModelBakery} private methods.<br>
 * All methods should be called in the {@link ModelRegistryEvent} event.
 *
 * @author HyCraftHD
 */
public class ModelUtil {
	
	static {
		if (ModelBakery.STATIC_DEFINITIONS instanceof ImmutableMap) {
			final Map<ResourceLocation, StateDefinition<Block, BlockState>> mutableMap = new HashMap<>();
			ModelBakery.STATIC_DEFINITIONS.forEach(mutableMap::put);
			ModelBakery.STATIC_DEFINITIONS = mutableMap;
		}
	}
	
	/**
	 * Replace the default state definition with a custom one. Can remove or add states for the model
	 *
	 * @param location Resource location of the model
	 * @param definition Custom state definition
	 */
	public static void addCustomStateContainer(ResourceLocation location, StateDefinition<Block, BlockState> definition) {
		ModelBakery.STATIC_DEFINITIONS.put(location, definition);
	}
	
	/**
	 * Add {@link Material} to be loaded to the atlas texture and stitched.
	 *
	 * @param material Render Material
	 */
	public static void addTexture(Material material) {
		ModelBakery.UNREFERENCED_TEXTURES.add(material);
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

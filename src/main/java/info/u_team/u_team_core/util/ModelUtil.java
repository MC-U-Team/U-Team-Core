package info.u_team.u_team_core.util;

import static net.minecraft.client.renderer.model.ModelBakery.LOCATIONS_BUILTIN_TEXTURES;
import static net.minecraft.client.renderer.model.ModelBakery.STATE_CONTAINER_OVERRIDES;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;

/**
 * Utility methods for interaction with {@link ModelBakery} private methods.<br>
 * All methods should be called in the {@link ModelRegistryEvent} event.
 * 
 * @author HyCraftHD
 */
public class ModelUtil {
	
	static {
		if (STATIC_DEFINITIONS instanceof ImmutableMap) {
			final Map<ResourceLocation, StateContainer<Block, BlockState>> mutableMap = new HashMap<>();
			STATIC_DEFINITIONS.forEach(mutableMap::put);
			STATIC_DEFINITIONS = mutableMap;
		}
	}
	
	/**
	 * Replace the default state container with a custom one. Can remove or add states for the model
	 * 
	 * @param location Resource location of the model
	 * @param container Custom state container
	 */
	public static void addCustomStateContainer(ResourceLocation location, StateContainer<Block, BlockState> container) {
		STATIC_DEFINITIONS.put(location, container);
	}
	
	/**
	 * Add {@link RenderMaterial} to be loaded to the atlas texture and stitched.
	 * 
	 * @param material Render Material
	 */
	public static void addTexture(RenderMaterial material) {
		UNREFERENCED_TEXTURES.add(material);
	}
	
	/**
	 * Utility class for an empty state container
	 * 
	 * @author HyCraftHD
	 */
	public static class EmptyStateContainer extends StateContainer<Block, BlockState> {
		
		public EmptyStateContainer(Block block) {
			super(Block::defaultBlockState, block, BlockState::new, new HashMap<>());
		}
		
		@Override
		public ImmutableList<BlockState> getPossibleStates() {
			return getOwner().getStateDefinition().getPossibleStates();
		}
		
	}
	
}

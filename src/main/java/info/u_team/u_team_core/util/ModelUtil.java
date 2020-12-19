package info.u_team.u_team_core.util;

import static net.minecraft.client.renderer.model.ModelBakery.*;

import java.util.*;

import com.google.common.collect.*;

import net.minecraft.block.*;
import net.minecraft.client.renderer.model.*;
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
		if (STATE_CONTAINER_OVERRIDES instanceof ImmutableMap) {
			final Map<ResourceLocation, StateContainer<Block, BlockState>> mutableMap = new HashMap<>();
			STATE_CONTAINER_OVERRIDES.forEach(mutableMap::put);
			STATE_CONTAINER_OVERRIDES = mutableMap;
		}
	}
	
	/**
	 * Replace the default state container with a custom one. Can remove or add states for the model
	 * 
	 * @param location Resource location of the model
	 * @param container Custom state container
	 */
	public static void addCustomStateContainer(ResourceLocation location, StateContainer<Block, BlockState> container) {
		STATE_CONTAINER_OVERRIDES.put(location, container);
	}
	
	/**
	 * Add {@link RenderMaterial} to be loaded to the atlas texture and stitched.
	 * 
	 * @param material Render Material
	 */
	public static void addTexture(RenderMaterial material) {
		LOCATIONS_BUILTIN_TEXTURES.add(material);
	}
	
	/**
	 * Utility class for an empty state container
	 * 
	 * @author HyCraftHD
	 */
	public static class EmptyStateContainer extends StateContainer<Block, BlockState> {
		
		public EmptyStateContainer(Block block) {
			super(Block::getDefaultState, block, BlockState::new, new HashMap<>());
		}
		
		@Override
		public ImmutableList<BlockState> getValidStates() {
			return getOwner().getStateContainer().getValidStates();
		}
		
	}
	
}

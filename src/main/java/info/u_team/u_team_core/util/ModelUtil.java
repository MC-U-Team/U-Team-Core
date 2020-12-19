package info.u_team.u_team_core.util;

import static net.minecraft.client.renderer.model.ModelBakery.*;

import java.util.*;

import com.google.common.collect.*;

import net.minecraft.block.*;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;

public class ModelUtil {
	
	static {
		if (STATE_CONTAINER_OVERRIDES instanceof ImmutableMap) {
			final Map<ResourceLocation, StateContainer<Block, BlockState>> mutableMap = new HashMap<>();
			STATE_CONTAINER_OVERRIDES.forEach(mutableMap::put);
			STATE_CONTAINER_OVERRIDES = mutableMap;
		}
	}
	
	public static void addCustomStateContainer(ResourceLocation location, StateContainer<Block, BlockState> container) {
		STATE_CONTAINER_OVERRIDES.put(location, container);
	}
	
	public static void addTexture(RenderMaterial material) {
		LOCATIONS_BUILTIN_TEXTURES.add(material);
	}
	
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

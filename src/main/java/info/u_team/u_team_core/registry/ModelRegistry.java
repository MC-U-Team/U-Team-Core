package info.u_team.u_team_core.registry;

import static info.u_team.u_team_core.registry.BlockRegistry.blocks;
import static info.u_team.u_team_core.registry.ItemRegistry.items;

import info.u_team.u_team_core.model.IModelProvider;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class ModelRegistry {
	
	@SubscribeEvent
	public static void event(ModelRegistryEvent event) {
		blocks.stream().filter(block -> block instanceof IModelProvider).map(block -> (IModelProvider) block).forEach(block -> block.registerModel());
		items.stream().filter(item -> item instanceof IModelProvider).map(item -> (IModelProvider) item).forEach(item -> item.registerModel());
	}
	
}

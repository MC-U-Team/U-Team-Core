package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.item.UBucketItem;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;

public class UCoreNeoForgeCapabilities {
	
	private static void register(RegisterCapabilitiesEvent event) {
		for (final Item item : RegistryUtil.getBuiltInRegistry(Registries.ITEM)) {
			if (item instanceof UBucketItem) {
				event.registerItem(Capabilities.FluidHandler.ITEM, (stack, context) -> new FluidBucketWrapper(stack), item);
			}
		}
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UCoreNeoForgeCapabilities::register);
	}
	
}

package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.item.UBucketItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

public class UCoreForgeCapabilities {
	
	private static void register(AttachCapabilitiesEvent<ItemStack> event) {
		if (event.getObject().getItem() instanceof UBucketItem) {
			event.addCapability(new ResourceLocation(UCoreMod.MODID, "ubucket_fluid"), new FluidBucketWrapper(event.getObject()));
		}
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addGenericListener(ItemStack.class, UCoreForgeCapabilities::register);
	}
	
}

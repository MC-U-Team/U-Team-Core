package info.u_team.u_team_test.init;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class TestCapabilities {
	
	private static void register(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TestBlockEntityTypes.BASIC_ENERGY_CREATOR.get(), (blockEntity, context) -> {
			return blockEntity.getSlots();
		});
		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, TestBlockEntityTypes.BASIC_ENERGY_CREATOR.get(), (blockEntity, context) -> {
			return blockEntity.getEnergy();
		});
		
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TestBlockEntityTypes.BASIC_FLUID_INVENTORY.get(), (blockEntity, context) -> {
			return blockEntity.getItemSlots();
		});
		event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, TestBlockEntityTypes.BASIC_FLUID_INVENTORY.get(), (blockEntity, context) -> {
			return blockEntity.getFluidTanks();
		});
	}
	
	static void registerMod(IEventBus bus) {
		bus.addListener(TestCapabilities::register);
	}
	
}

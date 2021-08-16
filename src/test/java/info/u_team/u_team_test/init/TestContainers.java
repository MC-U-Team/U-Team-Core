package info.u_team.u_team_test.init;

import info.u_team.u_team_core.containertype.UContainerType;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.BasicEnergyCreatorContainer;
import info.u_team.u_team_test.container.BasicFluidInventoryContainer;
import info.u_team.u_team_test.container.BasicTileEntityContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class TestContainers {
	
	public static final CommonDeferredRegister<MenuType<?>> CONTAINER_TYPES = CommonDeferredRegister.create(ForgeRegistries.CONTAINERS, TestMod.MODID);
	
	public static final RegistryObject<UContainerType<BasicTileEntityContainer>> BASIC = CONTAINER_TYPES.register("basic", () -> new UContainerType<>(BasicTileEntityContainer::new));
	
	public static final RegistryObject<MenuType<BasicEnergyCreatorContainer>> BASIC_ENERGY_CREATOR = CONTAINER_TYPES.register("energy_creator", () -> new UContainerType<>(BasicEnergyCreatorContainer::new));
	
	public static final RegistryObject<MenuType<BasicFluidInventoryContainer>> BASIC_FLUID_INVENTORY = CONTAINER_TYPES.register("fluid_inventory", () -> new UContainerType<>(BasicFluidInventoryContainer::new));
	
	public static void registerMod(IEventBus bus) {
		CONTAINER_TYPES.register(bus);
	}
}

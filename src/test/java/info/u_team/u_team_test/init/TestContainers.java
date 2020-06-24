package info.u_team.u_team_test.init;

import info.u_team.u_team_core.containertype.UContainerType;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.*;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.*;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestContainers {
	
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, TestMod.MODID);
	
	public static final ContainerType<BasicTileEntityContainer> BASIC = new UContainerType<>("basic", BasicTileEntityContainer::new);
	
	public static final ContainerType<BasicEnergyCreatorContainer> BASIC_ENERGY_CREATOR = new UContainerType<>("energy_creator", BasicEnergyCreatorContainer::new);
	
	public static final ContainerType<BasicFluidInventoryContainer> BASIC_FLUID_INVENTORY = new UContainerType<>("fluid_inventory", BasicFluidInventoryContainer::new);
	
	public static void register(IEventBus bus) {
		CONTAINER_TYPES.register(bus);
	}
}

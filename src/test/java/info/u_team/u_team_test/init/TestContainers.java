package info.u_team.u_team_test.init;

import info.u_team.u_team_core.containertype.UContainerType;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.*;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestContainers {
	
	public static final ContainerType<BasicTileEntityContainer> BASIC = new UContainerType<>("basic", BasicTileEntityContainer::new);
	
	public static final ContainerType<BasicEnergyCreatorContainer> BASIC_ENERGY_CREATOR = new UContainerType<>("energy_creator", BasicEnergyCreatorContainer::new);
	
	public static final ContainerType<BasicFluidInventoryContainer> BASIC_FLUID_INVENTORY = new UContainerType<>("fluid_inventory", BasicFluidInventoryContainer::new);
	
	@SubscribeEvent
	public static void register(Register<ContainerType<?>> event) {
		BaseRegistryUtil.getAllGenericRegistryEntriesAndApplyNames(TestMod.MODID, ContainerType.class).forEach(event.getRegistry()::register);
	}
}

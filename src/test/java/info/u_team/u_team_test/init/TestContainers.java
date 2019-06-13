package info.u_team.u_team_test.init;

import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.ContainerTileEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.*;

@EventBusSubscriber(modid = TestMod.modid, bus = Bus.FORGE)
public class TestContainers {
	
	public static final ContainerType<ContainerTileEntity> type = new ContainerType<ContainerTileEntity>(ContainerTileEntity::new);
	
	public static void construct() {
		type.setRegistryName(TestMod.modid, "type");
		ForgeRegistries.CONTAINERS.register(type);
		System.out.println("WRONG REGITRSDilfhdkfgkjhldfgjkl dfgbkyesj,fgbhukdk");
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<ContainerType<?>> event) {
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		IForgeRegistry<ContainerType<?>> registry = event.getRegistry();
		type.setRegistryName(TestMod.modid, "type");
		registry.register(type);
	}
}

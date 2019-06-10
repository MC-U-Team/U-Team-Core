package info.u_team.u_team_test.init;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.ContainerTileEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = UCoreMain.modid, bus = Bus.MOD)
public class TestContainers {
	
	public static final ContainerType<ContainerTileEntity> type = new ContainerType<ContainerTileEntity>(ContainerTileEntity::new);
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<ContainerType<?>> event) {
		IForgeRegistry<ContainerType<?>> registry = event.getRegistry();
		type.setRegistryName(TestMod.modid, "type");
		registry.register(type);
	}
}

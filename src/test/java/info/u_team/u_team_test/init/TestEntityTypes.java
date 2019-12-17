package info.u_team.u_team_test.init;

import info.u_team.u_team_core.entitytype.UEntityType;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.entity.BetterEnderPearlEntity;
import net.minecraft.entity.*;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestEntityTypes {
	
	public static final EntityType<BetterEnderPearlEntity> BETTER_ENDERPEARL = UEntityType.UBuilder.<BetterEnderPearlEntity> create("better_enderpearl", BetterEnderPearlEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(128).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true).build();
	
	@SubscribeEvent
	public static void register(Register<EntityType<?>> event) {
		// TODO disabled because of https://github.com/MinecraftForge/MinecraftForge/pull/6364
		// BaseRegistryUtil.getAllGenericRegistryEntriesAndApplyNames(TestMod.MODID,
		// EntityType.class).forEach(event.getRegistry()::register);
	}
	
}

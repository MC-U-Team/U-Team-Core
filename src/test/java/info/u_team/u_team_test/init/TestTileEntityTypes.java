package info.u_team.u_team_test.init;

import info.u_team.u_team_core.tileentitytype.UTileEntityType;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.tileentity.BasicTileEntityTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestTileEntityTypes {
	
	public static final TileEntityType<BasicTileEntityTileEntity> BASIC = UTileEntityType.UBuilder.create("tileentity", BasicTileEntityTileEntity::new, TestBlocks.BASIC_TILEENTITY).build();
	
	@SubscribeEvent
	public static void register(Register<TileEntityType<?>> event) {
		BaseRegistryUtil.getAllGenericRegistryEntriesAndApplyNames(TestMod.MODID, TileEntityType.class).forEach(event.getRegistry()::register);
	}
	
}

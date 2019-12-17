package info.u_team.u_team_test.init;

import java.util.List;

import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.block.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestBlocks {
	
	public static final BasicBlock BASIC = new BasicBlock("basicblock");
	
	public static final BasicTileEntityBlock BASIC_TILEENTITY = new BasicTileEntityBlock("tileentity");
	
	public static final BasicEnergyCreatorBlock BASIC_ENERGY_CREATOR = new BasicEnergyCreatorBlock("energy_creator");
	
	@SubscribeEvent
	public static void register(Register<Block> event) {
		entries = BaseRegistryUtil.getAllRegistryEntriesAndApplyNames(TestMod.MODID, Block.class);
		entries.forEach(event.getRegistry()::register);
	}
	
	@SubscribeEvent
	public static void registerBlockItem(Register<Item> event) {
		BaseRegistryUtil.getBlockItems(entries).forEach(event.getRegistry()::register);
	}
	
	// Just a cache for the block item registry for performance
	private static List<Block> entries;
	
}

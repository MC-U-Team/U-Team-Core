package info.u_team.u_team_test.init;

import info.u_team.u_team_test.TestMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class TestCreativeTabs {
	
	public static CreativeModeTab TAB;
	
	private static void register(CreativeModeTabEvent.Register event) {
		TAB = event.registerCreativeModeTab(new ResourceLocation(TestMod.MODID, "tab"), builder -> {
			builder.icon(() -> new ItemStack(TestBlocks.BASIC.get()));
			builder.title(Component.translatable("creativetabs.uteamtest.tab"));
			builder.displayItems((enabledFeatures, output, displayOperatorCreativeTab) -> {
				TestBlocks.BLOCKS.itemIterable().forEach(item -> {
					output.accept(item);
				});
				TestItems.ITEMS.entryIterable().forEach(item -> {
					output.accept(item);
				});
			});
		});
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(TestCreativeTabs::register);
	}
	
}

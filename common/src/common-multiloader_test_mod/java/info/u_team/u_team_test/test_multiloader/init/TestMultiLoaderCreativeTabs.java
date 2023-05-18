package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.CreativeModeTabRegister;
import info.u_team.u_team_core.api.registry.ResourceEntry;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TestMultiLoaderCreativeTabs {
	
	public static final CreativeModeTabRegister CREATIVE_TABS = CreativeModeTabRegister.create(TestMultiLoaderReference.MODID);
	
	public static final ResourceEntry<CreativeModeTab> TEST = CREATIVE_TABS.register("test_tab", builder -> {
		builder.icon(() -> new ItemStack(TestMultiLoaderBlocks.TEST.get()));
		builder.displayItems((parameters, output) -> {
			TestMultiLoaderBlocks.BLOCKS.itemIterable().forEach(item -> {
				output.accept(item);
			});
			TestMultiLoaderItems.ITEMS.entryIterable().forEach(item -> {
				output.accept(item);
			});
		});
	});
	
	public static void register() {
		CREATIVE_TABS.register();
	}
	
}

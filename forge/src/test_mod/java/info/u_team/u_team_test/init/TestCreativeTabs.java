package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.CreativeModeTabRegister;
import info.u_team.u_team_core.api.registry.ResourceEntry;
import info.u_team.u_team_test.TestMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TestCreativeTabs {
	
	public static final CreativeModeTabRegister CREATIVE_TABS = CreativeModeTabRegister.create(TestMod.MODID);
	
	public static final ResourceEntry<CreativeModeTab> TAB = CREATIVE_TABS.register("tab", builder -> {
		builder.icon(() -> new ItemStack(TestBlocks.BASIC.get()));
		builder.title(Component.translatable("creativetabs.uteamtest.tab"));
		builder.displayItems((parameters, output) -> {
			TestBlocks.BLOCKS.itemIterable().forEach(item -> {
				output.accept(item);
			});
			TestItems.ITEMS.entryIterable().forEach(item -> {
				output.accept(item);
			});
		});
	});
	
	public static void register() {
		CREATIVE_TABS.register();
	}
	
}

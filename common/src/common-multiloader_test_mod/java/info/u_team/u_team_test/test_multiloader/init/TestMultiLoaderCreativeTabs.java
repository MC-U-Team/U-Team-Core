package info.u_team.u_team_test.test_multiloader.init;

import java.util.stream.Stream;

import info.u_team.u_team_core.api.registry.CreativeModeTabRegister;
import info.u_team.u_team_core.api.registry.ResourceEntry;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

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
			Stream.of(Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION, Items.TIPPED_ARROW).forEach(item -> {
				TestMultiLoaderPotions.POTIONS.entryIterable().forEach(potion -> {
					output.accept(PotionUtils.setPotion(new ItemStack(item), potion), TabVisibility.PARENT_TAB_ONLY);
				});
			});
			output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(TestMultiLoaderEnchantments.TEST.get(), 1)), TabVisibility.PARENT_TAB_ONLY);
		});
	});
	
	static void register() {
		CREATIVE_TABS.register();
	}
	
}

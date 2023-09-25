package info.u_team.u_team_core.intern.recipe;

import java.util.List;

import com.google.common.collect.Lists;

import info.u_team.u_team_core.api.dye.DyeableItem;
import info.u_team.u_team_core.intern.init.UCoreRecipeSerializers;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class DyeableItemDyeRecipe extends CustomRecipe {
	
	public DyeableItemDyeRecipe(CraftingBookCategory category) {
		super(category);
	}
	
	@Override
	public boolean matches(CraftingContainer container, Level level) {
		ItemStack dyeableItem = ItemStack.EMPTY;
		final List<ItemStack> dyeList = Lists.newArrayList();
		
		for (int index = 0; index < container.getContainerSize(); ++index) {
			final ItemStack slotStack = container.getItem(index);
			if (!slotStack.isEmpty()) {
				final Item item = slotStack.getItem();
				if (item instanceof DyeableItem) {
					if (!dyeableItem.isEmpty()) {
						return false;
					}
					dyeableItem = slotStack;
				} else {
					if (!(item instanceof DyeItem)) {
						return false;
					}
					dyeList.add(slotStack);
				}
			}
		}
		
		return !dyeableItem.isEmpty() && !dyeList.isEmpty();
	}
	
	@Override
	public ItemStack assemble(CraftingContainer container, RegistryAccess registry) {
		ItemStack dyeableItem = ItemStack.EMPTY;
		final List<DyeItem> dyeItemList = Lists.newArrayList();
		
		for (int index = 0; index < container.getContainerSize(); ++index) {
			final ItemStack slotStack = container.getItem(index);
			if (!slotStack.isEmpty()) {
				final Item item = slotStack.getItem();
				if (item instanceof DyeableItem) {
					if (!dyeableItem.isEmpty()) {
						return ItemStack.EMPTY;
					}
					dyeableItem = slotStack.copy();
				} else {
					if (!(item instanceof DyeItem)) {
						return ItemStack.EMPTY;
					}
					dyeItemList.add((DyeItem) item);
				}
			}
		}
		
		if (!dyeableItem.isEmpty() && !dyeItemList.isEmpty()) {
			return DyeableItem.colorStackDyeItem(dyeableItem, dyeItemList);
		} else {
			return ItemStack.EMPTY;
		}
	}
	
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return UCoreRecipeSerializers.CRAFTING_SPECIAL_ITEMDYE.get();
	}
	
}

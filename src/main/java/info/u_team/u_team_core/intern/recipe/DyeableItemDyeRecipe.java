package info.u_team.u_team_core.intern.recipe;

import java.util.List;

import com.google.common.collect.Lists;

import info.u_team.u_team_core.api.dye.IDyeableItem;
import info.u_team.u_team_core.intern.init.UCoreRecipeSerializers;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class DyeableItemDyeRecipe extends SpecialRecipe {
	
	public DyeableItemDyeRecipe(ResourceLocation location) {
		super(location);
	}
	
	@Override
	public boolean matches(CraftingInventory inventory, World world) {
		ItemStack dyeableItem = ItemStack.EMPTY;
		final List<ItemStack> dyeList = Lists.newArrayList();
		
		for (int index = 0; index < inventory.getContainerSize(); ++index) {
			final ItemStack slotStack = inventory.getItem(index);
			if (!slotStack.isEmpty()) {
				final Item item = slotStack.getItem();
				if (item instanceof IDyeableItem) {
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
	public ItemStack assemble(CraftingInventory inventory) {
		ItemStack dyeableItem = ItemStack.EMPTY;
		final List<DyeItem> dyeItemList = Lists.newArrayList();
		
		for (int index = 0; index < inventory.getContainerSize(); ++index) {
			final ItemStack slotStack = inventory.getItem(index);
			if (!slotStack.isEmpty()) {
				final Item item = slotStack.getItem();
				if (item instanceof IDyeableItem) {
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
			return IDyeableItem.colorStackDyeItem(dyeableItem, dyeItemList);
		} else {
			return ItemStack.EMPTY;
		}
	}
	
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}
	
	@Override
	public IRecipeSerializer<?> getSerializer() {
		return UCoreRecipeSerializers.CRAFTING_SPECIAL_ITEMDYE.get();
	}
}

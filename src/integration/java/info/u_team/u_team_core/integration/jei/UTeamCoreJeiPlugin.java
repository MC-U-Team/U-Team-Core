package info.u_team.u_team_core.integration.jei;

import java.util.List;
import java.util.stream.Collectors;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.dye.IDyeableItem;
import info.u_team.u_team_core.inventory.*;
import mezz.jei.api.*;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.registries.ForgeRegistries;

@JeiPlugin
public class UTeamCoreJeiPlugin implements IModPlugin {
	
	private final ResourceLocation id = new ResourceLocation(UCoreMod.MODID, "jei");
	
	@Override
	public ResourceLocation getPluginUid() {
		return id;
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		final Minecraft minecraft = Minecraft.getInstance();
		final World world = minecraft.world;
		
		final IItemHandlerModifiable handler = new UItemStackHandler(9);
		handler.setStackInSlot(1, new ItemStack(Items.WHITE_DYE));
		
		final List<ItemStack> items = ForgeRegistries.ITEMS.getValues().stream() //
				.filter(item -> item instanceof IDyeableItem || item instanceof IDyeableArmorItem) //
				.map(ItemStack::new) //
				.filter(stack -> {
					handler.setStackInSlot(0, stack.copy());
					return world.getRecipeManager().getRecipe(IRecipeType.CRAFTING, new CraftingRecipeWrapper(handler, 3, 3), world).isPresent();
				}).collect(Collectors.toList());
		
		registration.addIngredientInfo(items, VanillaTypes.ITEM, new TranslationTextComponent("jei.uteamcore.dyeable.info"));
	}
	
}

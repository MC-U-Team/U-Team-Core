package info.u_team.u_team_core.integration.jei;

import java.util.stream.Collectors;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.dye.IDyeableItem;
import info.u_team.u_team_core.inventory.CraftingRecipeWrapper;
import info.u_team.u_team_core.inventory.UItemStackHandler;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
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
		final var minecraft = Minecraft.getInstance();
		final var level = minecraft.level;
		
		final var handler = new UItemStackHandler(9);
		handler.setStackInSlot(1, new ItemStack(Items.WHITE_DYE));
		
		final var items = ForgeRegistries.ITEMS.getValues().stream() //
				.filter(item -> item instanceof IDyeableItem || item instanceof DyeableLeatherItem) //
				.map(ItemStack::new) //
				.filter(stack -> {
					handler.setStackInSlot(0, stack.copy());
					return level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, new CraftingRecipeWrapper(handler, 3, 3), level).isPresent();
				}).collect(Collectors.toList());
		
		registration.addIngredientInfo(items, VanillaTypes.ITEM, new TranslatableComponent("jei.uteamcore.dyeable.info"));
	}
	
}

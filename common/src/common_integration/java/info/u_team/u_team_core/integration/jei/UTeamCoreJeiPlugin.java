package info.u_team.u_team_core.integration.jei;

import java.util.List;
import java.util.stream.Collectors;

import info.u_team.u_team_core.UCoreReference;
import info.u_team.u_team_core.api.dye.DyeableItem;
import info.u_team.u_team_core.inventory.DummyCraftingContainer;
import info.u_team.u_team_core.util.RegistryUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;

@JeiPlugin
public class UTeamCoreJeiPlugin implements IModPlugin {
	
	private final ResourceLocation id = new ResourceLocation(UCoreReference.MODID, "jei");
	
	@Override
	public ResourceLocation getPluginUid() {
		return id;
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		final ClientLevel level = Minecraft.getInstance().level;
		
		final DummyCraftingContainer container = new DummyCraftingContainer(3, 3);
		
		container.setItem(1, new ItemStack(Items.WHITE_DYE));
		
		final List<ItemStack> items = RegistryUtil.getBuiltInRegistry(Registries.ITEM).stream() //
				.filter(item -> item instanceof DyeableItem || item instanceof DyeableLeatherItem) //
				.map(ItemStack::new) //
				.filter(stack -> {
					container.setItem(0, stack.copy());
					return level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, container, level).isPresent();
				}).collect(Collectors.toList());
		
		registration.addIngredientInfo(items, VanillaTypes.ITEM_STACK, Component.translatable("jei.uteamcore.dyeable.info"));
	}
	
}

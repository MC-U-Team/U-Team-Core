package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.ingredient.ItemIngredient;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegisterEvent;

public class UCoreIngredients {
	
	private static void registerIngredient(RegisterEvent event) {
		if (event.getRegistryKey().equals(Registries.RECIPE_SERIALIZER)) {
			CraftingHelper.register(new ResourceLocation(UCoreMod.MODID, "item"), ItemIngredient.Serializer.INSTANCE);
		}
	}
	
	static void registerMod(IEventBus bus) {
		bus.addListener(UCoreIngredients::registerIngredient);
	}
	
}

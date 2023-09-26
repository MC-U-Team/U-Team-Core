package info.u_team.u_team_core.intern.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegisterEvent;

public class UCoreIngredients {
	
	private static void registerIngredient(RegisterEvent event) {
		// if (event.getRegistryKey().equals(Registries.RECIPE_SERIALIZER)) {
		// CraftingHelper.register(new ResourceLocation(UCoreMod.MODID, "item"), ItemIngredient.Serializer.INSTANCE);
		// }
		// TODO not working anymore. Needed?
	}
	
	static void registerMod(IEventBus bus) {
		bus.addListener(UCoreIngredients::registerIngredient);
	}
	
}

package info.u_team.u_team_core.integration.jei;

// TODO uncomment when jei is available again
//import java.util.List;
//import java.util.stream.Collectors;
//
//import info.u_team.u_team_core.UCoreMod;
//import info.u_team.u_team_core.api.dye.IDyeableItem;
//import mezz.jei.api.*;
//import mezz.jei.api.constants.VanillaTypes;
//import mezz.jei.api.registration.IRecipeRegistration;
//import net.minecraft.item.*;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.registries.ForgeRegistries;
//
//@JeiPlugin
//public class UTeamCoreJeiPlugin implements IModPlugin {
//	
//	private final ResourceLocation id = new ResourceLocation(UCoreMod.MODID, "jei");
//	
//	@Override
//	public ResourceLocation getPluginUid() {
//		return id;
//	}
//	
//	@Override
//	public void registerRecipes(IRecipeRegistration registration) {
//		final List<ItemStack> items = ForgeRegistries.ITEMS.getValues().stream().filter(item -> item instanceof IDyeableItem || item instanceof IDyeableArmorItem).map(ItemStack::new).collect(Collectors.toList());
//		registration.addIngredientInfo(items, VanillaTypes.ITEM, "jei.uteamcore.dyeable.info");
//	}
//	
//}

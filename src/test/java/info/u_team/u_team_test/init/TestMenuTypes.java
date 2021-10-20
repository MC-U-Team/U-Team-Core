package info.u_team.u_team_test.init;

import info.u_team.u_team_core.menutype.UMenuType;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.menu.BasicEnergyCreatorMenu;
import info.u_team.u_team_test.menu.BasicFluidInventoryContainer;
import info.u_team.u_team_test.menu.BasicTileEntityContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class TestMenuTypes {
	
	public static final CommonDeferredRegister<MenuType<?>> MENU_TYPES = CommonDeferredRegister.create(ForgeRegistries.CONTAINERS, TestMod.MODID);
	
	public static final RegistryObject<UMenuType<BasicTileEntityContainer>> BASIC_BLOCK_ENTITY = MENU_TYPES.register("basic_block_entity", () -> new UMenuType<>(BasicTileEntityContainer::new));
	public static final RegistryObject<MenuType<BasicEnergyCreatorMenu>> BASIC_ENERGY_CREATOR = MENU_TYPES.register("energy_creator", () -> new UMenuType<>(BasicEnergyCreatorMenu::new));
	public static final RegistryObject<MenuType<BasicFluidInventoryContainer>> BASIC_FLUID_INVENTORY = MENU_TYPES.register("fluid_inventory", () -> new UMenuType<>(BasicFluidInventoryContainer::new));
	
	public static void registerMod(IEventBus bus) {
		MENU_TYPES.register(bus);
	}
}

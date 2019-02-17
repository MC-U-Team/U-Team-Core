package info.u_team.u_team_test;

import info.u_team.u_team_test.proxy.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TestMod.modid)
public class TestMod {
	
	public static final String modid = "uteamtest";
	
	public TestMod() {
		System.out.println("--------------------------------------- LOADING TEST MOD ---------------------------------------");
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		CommonProxy.construct();
	}
	
	@SubscribeEvent
	public void common(FMLCommonSetupEvent event) {
		CommonProxy.setup();
	}
	
	@SubscribeEvent
	public void client(final FMLClientSetupEvent event) {
		ClientProxy.setup();
	}
	
}

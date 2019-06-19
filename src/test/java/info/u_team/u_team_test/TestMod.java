package info.u_team.u_team_test;

import info.u_team.u_team_core.api.IModProxy;
import info.u_team.u_team_test.proxy.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TestMod.MODID)
public class TestMod {
	
	public static final String MODID = "uteamtest";
	
	private static final IModProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	
	public TestMod() {
		System.out.println("--------------------------------------- LOADING TEST MOD ---------------------------------------");
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		PROXY.construct();
	}
	
	@SubscribeEvent
	public void set(FMLCommonSetupEvent event) {
		PROXY.setup();
	}
	
	@SubscribeEvent
	public void complete(FMLLoadCompleteEvent event) {
		PROXY.complete();
	}
	
}

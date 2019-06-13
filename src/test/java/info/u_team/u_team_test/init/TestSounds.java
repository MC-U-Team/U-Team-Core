package info.u_team.u_team_test.init;

import info.u_team.u_team_core.sound.USoundEvent;
import info.u_team.u_team_core.util.registry.SoundEventRegistry;
import info.u_team.u_team_test.TestMod;
import net.minecraft.util.*;

public class TestSounds {
	
	public static final SoundEvent better_enderpearl_use = new USoundEvent(new ResourceLocation(TestMod.modid, "better_enderpearl_use"));
	
	public static void construct() {
		SoundEventRegistry.register(TestMod.modid, TestSounds.class);
	}
	
}

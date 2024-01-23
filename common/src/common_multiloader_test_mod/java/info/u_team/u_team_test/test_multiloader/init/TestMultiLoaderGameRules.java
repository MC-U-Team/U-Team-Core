package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.GameRuleRegister;
import info.u_team.u_team_core.api.registry.LazyEntry;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;

public class TestMultiLoaderGameRules {
	
	public static final GameRuleRegister GAME_RULES = GameRuleRegister.create();
	
	public static final LazyEntry<GameRules.Key<BooleanValue>> RULE_DO_RADIATION_DAMAGE = GAME_RULES.register(TestMultiLoaderReference.MODID, "doTestEffectDamage", GameRules.Category.PLAYER, () -> GameRules.BooleanValue.create(true));
	
	static void register() {
		GAME_RULES.register();
	}
	
}

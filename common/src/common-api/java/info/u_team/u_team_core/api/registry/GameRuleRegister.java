package info.u_team.u_team_core.api.registry;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Supplier;

import com.google.common.collect.Streams;

import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.world.level.GameRules;

public interface GameRuleRegister extends Iterable<LazyEntry<GameRules.Key<?>>> {
	
	static GameRuleRegister create() {
		return Factory.INSTANCE.create();
	}
	
	<T extends GameRules.Value<T>> LazyEntry<GameRules.Key<T>> register(String name, GameRules.Category category, Supplier<? extends GameRules.Type<T>> type);
	
	void register();
	
	Collection<LazyEntry<GameRules.Key<?>>> getEntries();
	
	@Override
	default Iterator<LazyEntry<GameRules.Key<?>>> iterator() {
		return getEntries().iterator();
	}
	
	default Iterable<GameRules.Key<?>> entryIterable() {
		return () -> CastUtil.uncheckedCast(Streams.stream(this).map(LazyEntry::get).iterator());
	}
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
		
		GameRuleRegister create();
	}
	
}

package info.u_team.u_team_core.impl.common;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.GameRuleRegister;
import info.u_team.u_team_core.api.registry.LazyEntry;
import info.u_team.u_team_core.util.CastUtil;
import net.minecraft.world.level.GameRules;

public abstract class CommonGameRuleRegister implements GameRuleRegister {
	
	protected final Map<GameRuleLazyEntry<?>, GameRuleData<?>> entries;
	
	protected CommonGameRuleRegister() {
		entries = new LinkedHashMap<>();
	}
	
	@Override
	public <T extends GameRules.Value<T>> LazyEntry<GameRules.Key<T>> register(String name, GameRules.Category category, Supplier<? extends GameRules.Type<T>> type) {
		final GameRuleLazyEntry<T> entry = new GameRuleLazyEntry<>();
		entries.put(entry, new GameRuleData<>(name, category, type));
		return entry;
	}
	
	@Override
	public Collection<LazyEntry<GameRules.Key<?>>> getEntries() {
		return CastUtil.uncheckedCast(entries.keySet());
	}
	
	protected void registerEntries() {
		for (final Entry<GameRuleLazyEntry<?>, GameRuleData<?>> entry : entries.entrySet()) {
			final GameRuleLazyEntry<?> registryEntry = entry.getKey();
			
			final GameRuleData<?> data = entry.getValue();
			final GameRules.Key<?> key = GameRules.register(data.name, data.category, data.type.get());
			
			registryEntry.updateReference(key);
		}
	}
	
	protected record GameRuleData<T extends GameRules.Value<T>>(String name, GameRules.Category category, Supplier<? extends GameRules.Type<T>> type) {
	}
	
	public static class GameRuleLazyEntry<T extends GameRules.Value<T>> implements LazyEntry<GameRules.Key<T>> {
		
		private GameRules.Key<T> value;
		
		GameRuleLazyEntry() {
		}
		
		void updateReference(GameRules.Key<?> ruleKey) {
			value = CastUtil.uncheckedCast(ruleKey);
		}
		
		@Override
		public GameRules.Key<T> get() {
			Objects.requireNonNull(value, () -> "Gamerule not present");
			return value;
		}
		
		@Override
		public boolean isPresent() {
			return value != null;
		}
	}
	
}

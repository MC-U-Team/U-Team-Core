package info.u_team.u_team_core.impl;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.mojang.datafixers.util.Pair;

import info.u_team.u_team_core.item.food.UFoodPropertiesBuilder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

public class NeoForgeFoodProperties extends FoodProperties {
	
	private final List<Pair<Supplier<MobEffectInstance>, Float>> effects;
	
	@SuppressWarnings("deprecation")
	NeoForgeFoodProperties(int nutrition, float saturationModifier, boolean isMeat, boolean canAlwaysEat, boolean fastFood, List<Pair<Supplier<MobEffectInstance>, Float>> effects) {
		super(nutrition, saturationModifier, isMeat, canAlwaysEat, fastFood, Collections.emptyList());
		this.effects = effects;
	}
	
	@Override
	public List<Pair<MobEffectInstance, Float>> getEffects() {
		return effects.stream().map(pair -> Pair.of(pair.getFirst().get(), pair.getSecond())).collect(Collectors.toList());
	}
	
	public static class Creator implements UFoodPropertiesBuilder.Creator {
		
		@Override
		public FoodProperties create(int nutrition, float saturationModifier, boolean isMeat, boolean canAlwaysEat, boolean fastFood, List<Pair<Supplier<MobEffectInstance>, Float>> effects) {
			return new NeoForgeFoodProperties(nutrition, saturationModifier, isMeat, canAlwaysEat, fastFood, effects);
		}
	}
}
package info.u_team.u_team_core.item.food;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

public class UFoodPropertiesBuilder extends FoodProperties.Builder {
	
	public static UFoodPropertiesBuilder builder() {
		return new UFoodPropertiesBuilder();
	}
	
	private static final Creator CREATOR = ServiceUtil.loadOne(Creator.class);
	
	private final List<Pair<Supplier<MobEffectInstance>, Float>> effects = new ArrayList<>();
	
	protected UFoodPropertiesBuilder() {
	}
	
	@Override
	public UFoodPropertiesBuilder nutrition(int nutrition) {
		super.nutrition(nutrition);
		return this;
	}
	
	@Override
	public UFoodPropertiesBuilder saturationMod(float saturationModifier) {
		super.saturationMod(saturationModifier);
		return this;
	}
	
	@Override
	public UFoodPropertiesBuilder meat() {
		super.meat();
		return this;
	}
	
	@Override
	public UFoodPropertiesBuilder alwaysEat() {
		super.alwaysEat();
		return this;
	}
	
	@Override
	public UFoodPropertiesBuilder fast() {
		super.fast();
		return this;
	}
	
	@Override
	public UFoodPropertiesBuilder effect(MobEffectInstance effect, float probability) {
		return effect(() -> effect, probability);
	}
	
	public UFoodPropertiesBuilder effect(Supplier<MobEffectInstance> effectSupplier, float probability) {
		effects.add(Pair.of(Suppliers.memoize(effectSupplier::get), probability));
		return this;
	}
	
	@Override
	public FoodProperties build() {
		return CREATOR.create(nutrition, saturationModifier, isMeat, canAlwaysEat, fastFood, effects);
	}
	
	public static interface Creator {
		
		FoodProperties create(int nutrition, float saturationModifier, boolean isMeat, boolean canAlwaysEat, boolean fastFood, List<Pair<Supplier<MobEffectInstance>, Float>> effects);
	}
	
}

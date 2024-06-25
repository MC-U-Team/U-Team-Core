package info.u_team.u_team_core.item.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

public class UFoodPropertiesBuilder extends FoodProperties.Builder {
	
	public static UFoodPropertiesBuilder builder() {
		return new UFoodPropertiesBuilder();
	}
	
	protected UFoodPropertiesBuilder() {
	}
	
	@Override
	public UFoodPropertiesBuilder nutrition(int nutrition) {
		super.nutrition(nutrition);
		return this;
	}
	
	@Override
	public UFoodPropertiesBuilder saturationModifier(float saturationModifier) {
		super.saturationModifier(saturationModifier);
		return this;
	}
	
	@Override
	public UFoodPropertiesBuilder alwaysEdible() {
		super.alwaysEdible();
		return this;
	}
	
	@Override
	public UFoodPropertiesBuilder fast() {
		super.fast();
		return this;
	}
	
	public UFoodPropertiesBuilder eatSeconds(float eatSeconds) {
		super.eatSeconds = eatSeconds;
		return this;
	}
	
	@Override
	public UFoodPropertiesBuilder effect(MobEffectInstance effect, float probability) {
		super.effect(effect, probability);
		return this;
	}
	
}

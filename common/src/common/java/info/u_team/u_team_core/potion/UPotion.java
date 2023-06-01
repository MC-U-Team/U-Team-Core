package info.u_team.u_team_core.potion;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class UPotion extends Potion {
	
	private final Supplier<List<MobEffectInstance>> effectsSupplier;
	
	public UPotion(Supplier<List<MobEffectInstance>> supplier) {
		this(null, supplier);
	}
	
	public UPotion(String name, Supplier<List<MobEffectInstance>> supplier) {
		super(name);
		effectsSupplier = Suppliers.memoize(supplier::get);
	}
	
	@Override
	public List<MobEffectInstance> getEffects() {
		return effectsSupplier.get();
	}
	
	@Override
	public boolean hasInstantEffects() {
		final List<MobEffectInstance> effects = effectsSupplier.get();
		if (!effects.isEmpty()) {
			for (final MobEffectInstance effect : effects) {
				if (effect.getEffect().isInstantenous()) {
					return true;
				}
			}
		}
		return false;
	}
	
}

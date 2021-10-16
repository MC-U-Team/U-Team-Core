package info.u_team.u_team_core.item.armor;

import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UArmorMaterialVanilla extends UArmorMaterial {
	
	protected final String texture;
	
	public UArmorMaterialVanilla(int durability, int[] armorPoints, int enchantability, Supplier<SoundEvent> soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> ingredient) {
		this(null, durability, armorPoints, enchantability, soundEvent, toughness, knockbackResistance, ingredient);
	}
	
	public UArmorMaterialVanilla(String texture, int durability, int[] armorpoints, int enchantability, Supplier<SoundEvent> soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> ingredient) {
		super(createDurabilityVanillaArray(durability), armorpoints, enchantability, soundEvent, toughness, knockbackResistance, ingredient);
		this.texture = texture;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public String getName() {
		return texture == null ? super.getName() : texture;
	}
	
	private static final int[] BASE_DURABILITY = new int[] { 13, 15, 16, 11 };
	
	private static int[] createDurabilityVanillaArray(int durability) {
		final var array = new int[4];
		for (var i = 0; i < array.length; i++) {
			array[i] = BASE_DURABILITY[i] * durability;
		}
		return array;
	}
	
}

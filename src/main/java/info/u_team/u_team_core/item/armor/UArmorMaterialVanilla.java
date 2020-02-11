package info.u_team.u_team_core.item.armor;

import java.util.function.Supplier;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.*;

public class UArmorMaterialVanilla extends UArmorMaterial {
	
	protected final String texture;
	
	public UArmorMaterialVanilla(int durability, int[] armorPoints, int enchantability, SoundEvent soundevent, float toughness, Supplier<Ingredient> ingredient) {
		this(null, durability, armorPoints, enchantability, soundevent, toughness, ingredient);
	}
	
	public UArmorMaterialVanilla(String texture, int durability, int[] armorpoints, int enchantability, SoundEvent soundevent, float toughness, Supplier<Ingredient> ingredient) {
		super(createDurabilityVanillaArray(durability), armorpoints, enchantability, soundevent, toughness, ingredient);
		this.texture = texture;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public String getName() {
		return texture == null ? super.getName() : texture;
	}
	
	private static final int[] baseDurability = new int[] { 13, 15, 16, 11 };
	
	private static int[] createDurabilityVanillaArray(int durability) {
		final int[] array = new int[4];
		for (int i = 0; i < array.length; i++) {
			array[i] = baseDurability[i] * durability;
		}
		return array;
	}
	
}

package info.u_team.u_team_test.init;

import java.util.EnumMap;
import java.util.List;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.util.RegistryUtil;
import info.u_team.u_team_test.TestMod;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class TestArmorMaterials {
	
	public static final CommonRegister<ArmorMaterial> ARMOR_MATERIALS = CommonRegister.create(Registries.ARMOR_MATERIAL, TestMod.MODID);
	
	public static final RegistryEntry<ArmorMaterial> BASIC = ARMOR_MATERIALS.register("basic", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
		map.put(ArmorItem.Type.BOOTS, 5);
		map.put(ArmorItem.Type.LEGGINGS, 6);
		map.put(ArmorItem.Type.CHESTPLATE, 8);
		map.put(ArmorItem.Type.HELMET, 2);
	}), 20, RegistryUtil.getBuiltInRegistry(Registries.SOUND_EVENT).wrapAsHolder(SoundEvents.PARROT_IMITATE_GUARDIAN), () -> Ingredient.of(Items.ZOMBIE_HEAD), List.of(), 1, 1));
	
	static void register() {
		ARMOR_MATERIALS.register();
	}
	
}

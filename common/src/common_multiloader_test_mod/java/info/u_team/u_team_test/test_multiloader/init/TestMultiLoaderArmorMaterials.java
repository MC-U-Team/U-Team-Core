package info.u_team.u_team_test.test_multiloader.init;

import java.util.EnumMap;
import java.util.List;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.util.RegistryUtil;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class TestMultiLoaderArmorMaterials {
	
	public static final CommonRegister<ArmorMaterial> ARMOR_MATERIALS = CommonRegister.create(Registries.ARMOR_MATERIAL, TestMultiLoaderReference.MODID);
	
	public static final RegistryEntry<ArmorMaterial> TEST = ARMOR_MATERIALS.register("test", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
		map.put(ArmorItem.Type.BOOTS, 5);
		map.put(ArmorItem.Type.LEGGINGS, 6);
		map.put(ArmorItem.Type.CHESTPLATE, 8);
		map.put(ArmorItem.Type.HELMET, 2);
	}), 20, RegistryUtil.getBuiltInRegistry(Registries.SOUND_EVENT).wrapAsHolder(SoundEvents.BEACON_ACTIVATE), () -> Ingredient.of(TestMultiLoaderItems.TEST.get()), List.of(), 1, 1));
	
	static void register() {
		ARMOR_MATERIALS.register();
	}
	
}

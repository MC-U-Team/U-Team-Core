package info.u_team.u_team_test;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.api.registry.IUArrayRegistryType;
import info.u_team.u_team_core.item.armor.*;
import info.u_team.u_team_core.item.tool.*;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import info.u_team.u_team_test.init.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.world.biome.*;

public class MyTest {
	
	public static Item test = new Item(new Properties());
	public static Item test2 = new Item(new Properties());
	public static Item test3 = new Item(new Properties());
	
	public static final IToolMaterial basic = new UToolMaterial(new int[] { 8, 0, 4, 2, 6 }, new float[] { -3.1F, -1F, -2F, -2F, 0F }, 2, 500, 5F, 8, 30, () -> Ingredient.fromItems(TestItems.basic));
	
	public static ToolSet set = ToolSetCreator.create("test", new Properties(), basic);
	
	public static final IArmorMaterial basic2 = new UArmorMaterialVanilla(20, new int[] { 5, 6, 8, 2 }, 20, TestSounds.better_enderpearl_use, 1, () -> Ingredient.fromItems(TestItems.basic));
	
	public static final ArmorSet basicarmor = ArmorSetCreator.create("basicarmor", TestItemGroups.group, new Properties(), basic2);
	
	public static Block block2 = new Block(net.minecraft.block.Block.Properties.from(Blocks.ACACIA_BUTTON));
	
	public static IUArrayRegistryType<Biome> biomes = () -> new Biome[] { Biomes.BADLANDS };
	
	public static void setup() {
		System.out.println(BaseRegistryUtil.getClassEntries(Item.class));
		System.out.println(BaseRegistryUtil.getClassEntriesFromArrayType(Item.class));
		System.out.println(BaseRegistryUtil.getClassEntriesFromArrayType(Biome.class));
	}
	
}

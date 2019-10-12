package test;

import info.u_team.u_team_core.api.IMetaType;
import info.u_team.u_team_core.block.*;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.entity.UEntityEntry;
import info.u_team.u_team_core.item.*;
import info.u_team.u_team_core.item.armor.*;
import info.u_team.u_team_core.registry.*;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import info.u_team.u_team_core.util.*;
import net.minecraft.block.material.Material;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod(modid = "test", name = "TestMod", version = "1.0.0")
public class TestMod {
	
	public static final UCreativeTab tab = new UCreativeTab("test", "tab");
	
	public static final UBlock block = new UBlock("testblock", Material.ROCK, tab);
	
	public static final UItem item = new UItem("testitem", tab);
	
	public static final UEntityEntry entity = new UEntityEntry(EntityEntryBuilder.create().entity(EntityTest.class).id(new ResourceLocation("test", "testentity"), 0).name("testentity").egg(0xFFFFFF, 0xAAAAAA).tracker(64, 20, false));
	public static final UEntityEntry entity2 = new UEntityEntry(EntityEntryBuilder.create().entity(EntityTest.class).id(new ResourceLocation("test", "testentity2"), 0).name("testentity2").egg(0xFFFFFF, 0xAAAAAA).tracker(64, 20, false));
	
	public static final UItemMetaData itemmeta = new UItemMetaData("testitemmeta", tab, EnumTest.values());
	
	public static final UBlockMetaData blockmeta = new UBlockMetaData("testblockmeta", Material.ROCK, tab, EnumTest.values());
	
	public static final SoundCategory category = EnumHelperSoundCategory.addSoundCategory("test");
	
	public static enum EnumTest implements IMetaType {
		
		FIRST(0, "first"),
		SECOND(1, "second"),
		THIRD(2, "third");
		
		private int meta;
		private String name;
		
		private EnumTest(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getMetadata() {
			return meta;
		}
		
	}
	
	public static final ArmorMaterial testarmor = EnumHelper.addArmorMaterial("test", "test:test", 100, new int[] { 2, 5, 6, 2 }, 10, SoundEvents.AMBIENT_CAVE, 0F);
	
	public static final UItemArmor[] testarmorarry = createArmor(testarmor, "test");
	
	private static UItemArmor[] createArmor(ArmorMaterial material, String name) {
		UItemArmor[] armor = new UItemArmor[4];
		armor[0] = new UItemHelmet(name, tab, material);
		armor[1] = new UItemChestplate(name, tab, material);
		armor[2] = new UItemLeggings(name, tab, material);
		armor[3] = new UItemBoots(name, tab, material);
		return armor;
	}
	
	@Deprecated
	public static UBlock asyncTeTest = new UBlockTileEntity("asyncte", Material.ROCK, new UTileEntityProvider(new ResourceLocation("test", "asyncte"), TileEntityAsyncTest.class));
	
	public static UBlock syncedTeTest = new BlockTileEntityTest("syncedte");
	
	public static UBlock testTeBuggy = new BlockTileEntityTestBuggy("testtebuggy");
	
	@SidedProxy(clientSide = "test.ClientProxyTest", serverSide = "test.CommonProxyTest")
	public static CommonProxyTest proxy;
	
	@EventHandler
	public void preinit(FMLConstructionEvent event) {
	}
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		tab.setIcon(block);
		BlockRegistry.register("test", block);
		ItemRegistry.register("test", item);
		// EntityRegistry.register("test", entity);
		
		EntityRegistry.register("test", RegistryUtil.getEntityRegistryEntries(TestMod.class));
		
		ItemRegistry.register("test", itemmeta);
		BlockRegistry.register("test", blockmeta);
		
		for (UItemArmor armor : testarmorarry) {
			ItemRegistry.register("test", armor);
		}
		
		BlockRegistry.register("test", asyncTeTest);
		BlockRegistry.register("test", syncedTeTest);
		BlockRegistry.register("test", testTeBuggy);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		asyncTeTest.setCreativeTab(tab);
		
		CommonRegistry.registerGuiHandler("test", new GuiHandlerTest());
		
		proxy.init();
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
	}
	
	@EventHandler
	public void postinit(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandBase() {
			
			@Override
			public String getUsage(ICommandSender sender) {
				return "/test";
			}
			
			@Override
			public String getName() {
				return "test";
			}
			
			@Override
			public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
				if (sender instanceof EntityPlayerMP) {
					EntityPlayerMP player = (EntityPlayerMP) sender;
					System.out.println("test");
					player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.BLOCK_ANVIL_BREAK, category, 1.0F, 1.0F);
				}
			}
		});
	}
	
}

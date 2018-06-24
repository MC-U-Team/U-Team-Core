package info.u_team.u_team_core.item;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.sub.USub;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * Item API<br>
 * -> Basic Item
 * 
 * @date 17.08.2017
 * @author MrTroble
 *
 */

public class UItem extends Item {
	
	public UItem(String name) {
		this(name, null);
	}
	
	public UItem(String name, UCreativeTab tab) {
		super();
		
		setRegistryName(USub.getID(), name);
		setUnlocalizedName(USub.getID() + ":" + name);
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
		register();
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.openGui("test", 0, worldIn, 0, 0, 0);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	private final void register() {
		ForgeRegistries.ITEMS.register(this);
	}
	
}

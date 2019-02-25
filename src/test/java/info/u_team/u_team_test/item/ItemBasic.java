package info.u_team.u_team_test.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.util.world.WorldUtil;
import info.u_team.u_team_test.init.TestItemGroups;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemBasic extends UItem {
	
	public ItemBasic(String name) {
		super(name, TestItemGroups.group, new Properties().rarity(EnumRarity.EPIC).defaultMaxDamage(10));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		
		if (!world.isRemote) {
			RayTraceResult raytrace = WorldUtil.rayTraceServerSide(player, 50);
			
			if (raytrace.type == Type.MISS) {
				player.sendStatusMessage(new TextComponentTranslation("item.uteamtest.basicitem.outofrange"), true);
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
			}
			
			BlockPos pos = raytrace.getBlockPos();
			
			EntityPlayerMP playermp = (EntityPlayerMP) player;
			playermp.connection.setPlayerLocation(pos.getX(), pos.getY() + 1, pos.getZ(), playermp.rotationYaw, playermp.rotationPitch);
			
			stack.damageItem(1, player);
			
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
}

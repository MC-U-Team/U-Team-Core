package info.u_team.u_team_test.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.util.world.WorldUtil;
import info.u_team.u_team_test.init.TestItemGroups;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ItemBasic extends UItem {
	
	public ItemBasic(String name) {
		super(name, TestItemGroups.group, new Properties().rarity(Rarity.EPIC).defaultMaxDamage(10));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);
		
		if (!world.isRemote) {
			RayTraceResult raytrace = WorldUtil.rayTraceServerSide(player, 50);
			
			if (raytrace.getType() == Type.MISS) {
				player.sendStatusMessage(new StringTextComponent("item.uteamtest.basicitem.outofrange"), true);
				return new ActionResult<>(ActionResultType.FAIL, stack);
			}
			
			Vec3d pos = raytrace.getHitVec();
			
			ServerPlayerEntity playermp = (ServerPlayerEntity) player;
			playermp.connection.setPlayerLocation(pos.getX(), pos.getY() + 1, pos.getZ(), playermp.rotationYaw, playermp.rotationPitch);
			
			stack.damageItem(1, player, (test) -> {
			});
			
		}
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
	}
}

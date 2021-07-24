package info.u_team.u_team_test.item;

import com.mojang.math.Vector3d;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.util.world.WorldUtil;
import info.u_team.u_team_test.init.TestItemGroups;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.item.ItemStack;

public class BasicItem extends UItem {
	
	public BasicItem() {
		super(TestItemGroups.GROUP, new Properties().rarity(Rarity.EPIC).defaultMaxDamage(10));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		final ItemStack stack = player.getHeldItem(hand);
		
		if (!world.isRemote) {
			final RayTraceResult rayTrace = WorldUtil.rayTraceServerSide(player, 50);
			
			if (rayTrace.getType() == Type.MISS) {
				player.sendStatusMessage(new TranslationTextComponent("item.uteamtest.basicitem.outofrange"), true);
				return new ActionResult<>(ActionResultType.FAIL, stack);
			}
			
			final Vector3d pos = rayTrace.getHitVec();
			((ServerPlayerEntity) player).connection.setPlayerLocation(pos.getX(), pos.getY() + 1, pos.getZ(), player.rotationYaw, player.rotationPitch);
			stack.damageItem(1, player, unused -> {
			});
			
		}
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
	}
}

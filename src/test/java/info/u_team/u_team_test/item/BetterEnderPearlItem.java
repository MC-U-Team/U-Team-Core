package info.u_team.u_team_test.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_test.entity.BetterEnderPearlEntity;
import info.u_team.u_team_test.init.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class BetterEnderPearlItem extends UItem {
	
	public BetterEnderPearlItem(String name) {
		super(name, TestItemGroups.GROUP, new Properties().rarity(Rarity.EPIC));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		final ItemStack stack = player.getHeldItem(hand);
		
		world.playSound(null, player.posX, player.posY, player.posZ, TestSounds.BETTER_ENDERPEARL_USE, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 1.5F));
		
		if (!world.isRemote) {
			final BetterEnderPearlEntity pearl = new BetterEnderPearlEntity(world, player);
			pearl.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 2.5F, 1.2F);
			world.addEntity(pearl);
		}
		return new ActionResult<>(ActionResultType.SUCCESS, stack);
	}
}

package info.u_team.u_team_test.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_test.entity.EntityBetterEnderPearl;
import info.u_team.u_team_test.init.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemBetterEnderPearl extends UItem {
	
	public ItemBetterEnderPearl(String name) {
		super(name, TestItemGroups.group, new Properties().rarity(EnumRarity.EPIC));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		
		world.playSound(null, player.posX, player.posY, player.posZ, TestSounds.better_enderpearl_use, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 1.5F));
		
		if (!world.isRemote) {
			EntityBetterEnderPearl pearl = new EntityBetterEnderPearl(world, player);
			pearl.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 2.5F, 1.2F);
			world.spawnEntity(pearl);
		}
		
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}
	
}

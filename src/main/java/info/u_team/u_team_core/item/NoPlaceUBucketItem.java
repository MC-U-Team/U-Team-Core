package info.u_team.u_team_core.item;

import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class NoPlaceUBucketItem extends UBucketItem {
	
	public NoPlaceUBucketItem(ItemGroup group, Properties properties, Supplier<? extends Fluid> fluid) {
		super(group, properties, fluid);
	}
	
	public NoPlaceUBucketItem(Properties properties, Supplier<? extends Fluid> fluid) {
		super(properties, fluid);
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		return new ActionResult<ItemStack>(ActionResultType.PASS, player.getItemInHand(hand));
	}
	
}

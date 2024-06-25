package info.u_team.u_team_core.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

public class NoPlaceUBucketItem extends UBucketItem {
	
	public NoPlaceUBucketItem(Properties properties, Fluid fluid) {
		super(properties, fluid);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
	}
	
}

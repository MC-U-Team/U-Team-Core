package info.u_team.u_team_core.item;

import java.util.function.Supplier;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

public class NoPlaceUBucketItem extends UBucketItem {
	
	public NoPlaceUBucketItem(Properties properties, Supplier<? extends Fluid> fluid) {
		this(null, properties, fluid);
	}
	
	public NoPlaceUBucketItem(CreativeModeTab creativeTab, Properties properties, Supplier<? extends Fluid> fluid) {
		super(creativeTab, properties, fluid);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
	}
	
}

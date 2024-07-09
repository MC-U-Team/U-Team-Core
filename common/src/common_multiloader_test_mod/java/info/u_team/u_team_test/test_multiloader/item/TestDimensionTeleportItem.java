package info.u_team.u_team_test.test_multiloader.item;

import info.u_team.u_team_core.item.UItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;

public class TestDimensionTeleportItem extends UItem {
	
	public TestDimensionTeleportItem() {
		super(new Properties().rarity(Rarity.EPIC).fireResistant().durability(10));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		final ItemStack stack = player.getItemInHand(hand);
		
		if (!level.isClientSide()) {
			stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
			
			final ServerLevel destination;
			if (Level.OVERWORLD == level.dimension()) {
				destination = level.getServer().getLevel(Level.NETHER);
			} else {
				destination = level.getServer().getLevel(Level.OVERWORLD);
			}
			
			player.changeDimension(new DimensionTransition(destination, player.position(), Vec3.ZERO, player.getYRot(), player.getXRot(), DimensionTransition.PLACE_PORTAL_TICKET));
		}
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}
	
}

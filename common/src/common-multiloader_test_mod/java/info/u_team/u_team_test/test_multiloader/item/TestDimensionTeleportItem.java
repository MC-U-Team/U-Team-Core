package info.u_team.u_team_test.test_multiloader.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.util.DimensionTeleportUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.PortalInfo;

public class TestDimensionTeleportItem extends UItem {
	
	public TestDimensionTeleportItem() {
		super(new Properties().rarity(Rarity.EPIC).fireResistant().defaultDurability(10));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		final ItemStack stack = player.getItemInHand(hand);
		
		if (!level.isClientSide()) {
			stack.hurtAndBreak(1, player, breakPlayer -> breakPlayer.broadcastBreakEvent(hand));
			
			final ServerLevel destination;
			if (Level.OVERWORLD == level.dimension()) {
				destination = level.getServer().getLevel(Level.NETHER);
			} else {
				destination = level.getServer().getLevel(Level.OVERWORLD);
			}
			DimensionTeleportUtil.changeDimension(player, destination, new PortalInfo(player.position(), player.getDeltaMovement(), player.getYRot(), player.getXRot()));
		}
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}
	
}

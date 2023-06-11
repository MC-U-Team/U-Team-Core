package info.u_team.u_team_test.test_multiloader.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.util.LevelUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;

public class TestUseItem extends UItem {
	
	public TestUseItem() {
		super(new Properties().rarity(Rarity.EPIC).defaultDurability(10));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		final ItemStack stack = player.getItemInHand(hand);
		
		if (!level.isClientSide()) {
			final HitResult rayTrace = LevelUtil.rayTraceServerSide(player, 50);
			
			if (rayTrace.getType() == Type.MISS) {
				player.displayClientMessage(Component.translatable(getDescriptionId() + ".outofrange").withStyle(ChatFormatting.DARK_RED), true);
				return InteractionResultHolder.fail(stack);
			}
			
			final Vec3 pos = rayTrace.getLocation();
			if (player instanceof final ServerPlayer serverPlayer) {
				serverPlayer.connection.teleport(pos.x(), pos.y() + 1, pos.z(), player.getYRot(), player.getXRot());
			}
			stack.hurtAndBreak(1, player, unused -> {
			});
			
		}
		return InteractionResultHolder.success(stack);
	}
	
	@Override
	public boolean shouldPlayUpdateAnimation(ItemStack oldStack, ItemStack newStack) {
		return false;
	}
	
	@Override
	public boolean canBeDropped(ItemStack stack, Player player) {
		return player.isCreative();
	}
}

package info.u_team.u_team_test.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.util.world.WorldUtil;
import info.u_team.u_team_test.init.TestItemGroups;
import net.minecraft.network.chat.TranslatableComponent;
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

public class BasicItem extends UItem {

	public BasicItem() {
		super(TestItemGroups.GROUP, new Properties().rarity(Rarity.EPIC).defaultDurability(10));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		final var stack = player.getItemInHand(hand);

		if (!world.isClientSide()) {
			final var rayTrace = WorldUtil.rayTraceServerSide(player, 50);

			if (rayTrace.getType() == Type.MISS) {
				player.displayClientMessage(new TranslatableComponent("item.uteamtest.basicitem.outofrange"), true);
				return InteractionResultHolder.fail(stack);
			}

			final var pos = rayTrace.getLocation();
			((ServerPlayer) player).connection.teleport(pos.x(), pos.y() + 1, pos.z(), player.getYRot(), player.getXRot());
			stack.hurtAndBreak(1, player, unused -> {
			});

		}
		return InteractionResultHolder.success(stack);
	}
}

package info.u_team.u_team_test.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_test.entity.BetterEnderPearlEntity;
import info.u_team.u_team_test.init.TestItemGroups;
import info.u_team.u_team_test.init.TestSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class BetterEnderPearlItem extends UItem {

	public BetterEnderPearlItem() {
		super(TestItemGroups.GROUP, new Properties().rarity(Rarity.EPIC));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		final var stack = player.getItemInHand(hand);

		world.playSound(null, player.getX(), player.getY(), player.getZ(), TestSounds.BETTER_ENDERPEARL_USE.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 1.5F));

		if (!world.isClientSide()) {
			final var pearl = new BetterEnderPearlEntity(world, player);
			pearl.shootFromRotation(player, player.getYRot(), player.getXRot(), 0.0F, 2.5F, 1.2F);
			world.addFreshEntity(pearl);
		}
		return InteractionResultHolder.success(stack);
	}
}

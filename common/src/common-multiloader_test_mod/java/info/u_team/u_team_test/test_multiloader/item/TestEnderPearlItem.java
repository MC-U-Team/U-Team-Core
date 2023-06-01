package info.u_team.u_team_test.test_multiloader.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_test.test_multiloader.entity.TestEnderPearl;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class TestEnderPearlItem extends UItem {
	
	public TestEnderPearlItem() {
		super(new Properties().rarity(Rarity.EPIC));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		final ItemStack stack = player.getItemInHand(hand);
		
		world.playSound(null, player.getX(), player.getY(), player.getZ(), TestMultiLoaderSoundEvents.TEST_ENDERPEARL_USE.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 1.5F));
		
		player.getCooldowns().addCooldown(this, 10);
		
		if (!world.isClientSide()) {
			final TestEnderPearl pearl = new TestEnderPearl(world, player);
			pearl.setItem(stack);
			pearl.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.2F);
			world.addFreshEntity(pearl);
		}
		
		player.awardStat(Stats.ITEM_USED.get(this));
		if (!player.getAbilities().instabuild) {
			stack.shrink(1);
		}
		
		return InteractionResultHolder.success(stack);
	}
}

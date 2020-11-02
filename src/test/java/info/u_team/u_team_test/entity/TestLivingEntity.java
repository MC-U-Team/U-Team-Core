package info.u_team.u_team_test.entity;

import java.util.Random;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

public class TestLivingEntity extends ZombieEntity {
	
	public TestLivingEntity(EntityType<? extends ZombieEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public static boolean func_223334_b(EntityType<HuskEntity> p_223334_0_, IServerWorld p_223334_1_, SpawnReason reason, BlockPos p_223334_3_, Random p_223334_4_) {
		return canMonsterSpawnInLight(p_223334_0_, p_223334_1_, reason, p_223334_3_, p_223334_4_) && (reason == SpawnReason.SPAWNER || p_223334_1_.canSeeSky(p_223334_3_));
	}
	
	@Override
	protected boolean shouldBurnInDay() {
		return false;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.BLOCK_LODESTONE_PLACE;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_ARROW_SHOOT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_DONKEY_DEATH;
	}
	
	@Override
	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_HOGLIN_HURT;
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity) {
		boolean flag = super.attackEntityAsMob(entity);
		if (flag && getHeldItemMainhand().isEmpty() && entity instanceof LivingEntity) {
			final float localDifficulty = world.getDifficultyForLocation(getPosition()).getAdditionalDifficulty();
			((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.LEVITATION, (int) (500 * localDifficulty)));
		}
		return flag;
	}
	
	@Override
	protected boolean shouldDrown() {
		return false;
	}
	
	@Override
	protected ItemStack getSkullDrop() {
		return new ItemStack(Items.GHAST_TEAR);
	}
}

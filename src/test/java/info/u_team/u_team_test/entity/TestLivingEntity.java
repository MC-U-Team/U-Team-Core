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
	
	public static boolean canSpawn(EntityType<TestLivingEntity> entityType, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
		return canMonsterSpawn(entityType, world, reason, pos, random) && (reason == SpawnReason.SPAWNER || world.canSeeSky(pos));
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
		final boolean flag = super.attackEntityAsMob(entity);
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

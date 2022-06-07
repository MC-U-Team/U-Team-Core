package info.u_team.u_team_test.entity;

import info.u_team.u_team_test.init.TestEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;

public class TestLiving extends Zombie {
	
	public TestLiving(Level world) {
		this(TestEntityTypes.TEST_LIVING.get(), world);
	}
	
	public TestLiving(EntityType<? extends Zombie> type, Level world) {
		super(type, world);
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		return Zombie.createAttributes() //
				.add(Attributes.ATTACK_DAMAGE, 6);
	}
	
	public static boolean checkTestLivingSpawnRules(EntityType<TestLiving> entityType, ServerLevelAccessor world, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return checkAnyLightMonsterSpawnRules(entityType, world, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWNER || world.canSeeSky(pos));
	}
	
	@Override
	protected boolean isSunSensitive() {
		return false;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.LODESTONE_PLACE;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ARROW_SHOOT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.DONKEY_DEATH;
	}
	
	@Override
	protected SoundEvent getStepSound() {
		return SoundEvents.HOGLIN_HURT;
	}
	
	@Override
	public boolean doHurtTarget(Entity entity) {
		final boolean flag = super.doHurtTarget(entity);
		if (flag && getMainHandItem().isEmpty() && entity instanceof final LivingEntity livingEntity) {
			final float localDifficulty = level.getCurrentDifficultyAt(blockPosition()).getEffectiveDifficulty();
			livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, (int) (500 * localDifficulty)));
		}
		return flag;
	}
	
	@Override
	protected boolean convertsInWater() {
		return false;
	}
	
	@Override
	protected ItemStack getSkull() {
		return new ItemStack(Items.GHAST_TEAR);
	}
	
	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}

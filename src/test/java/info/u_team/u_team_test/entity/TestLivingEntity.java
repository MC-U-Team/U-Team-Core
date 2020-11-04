package info.u_team.u_team_test.entity;

import java.util.Random;

import info.u_team.u_team_test.init.TestEntityTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.*;
import net.minecraft.network.IPacket;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraftforge.fml.network.NetworkHooks;

public class TestLivingEntity extends ZombieEntity {
	
	public TestLivingEntity(World world) {
		this(TestEntityTypes.TEST_LIVING.get(), world);
	}
	
	public TestLivingEntity(EntityType<? extends ZombieEntity> type, World world) {
		super(type, world);
	}
	
	public static MutableAttribute registerAttributes() {
		return ZombieEntity.func_234342_eQ_() //
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 6);
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
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}

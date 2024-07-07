package info.u_team.u_team_test.test_multiloader.entity;

import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderEntityTypes;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class TestEnderPearl extends ThrowableItemProjectile {
	
	public TestEnderPearl(EntityType<? extends TestEnderPearl> type, Level world) {
		super(type, world);
	}
	
	public TestEnderPearl(Level world, LivingEntity thrower) {
		super(TestMultiLoaderEntityTypes.TEST_ENDERPEARL.get(), thrower, world);
	}
	
	@Override
	protected Item getDefaultItem() {
		return TestMultiLoaderItems.TEST_ENDERPEARL.get();
	}
	
	@Override
	protected double getDefaultGravity() {
		return 0.05F;
	}
	
	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		result.getEntity().hurt(damageSources().thrown(this, getOwner()), 0);
	}
	
	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		
		for (int index = 0; index < 32; ++index) {
			level().addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + random.nextDouble() * 2.0D, this.getZ(), random.nextGaussian(), 0.0D, random.nextGaussian());
		}
		
		if (!level().isClientSide() && !isRemoved()) {
			final Entity entity = getOwner();
			if (entity instanceof final ServerPlayer player) {
				if (player.connection.isAcceptingMessages() && player.level() == level() && !player.isSleeping()) {
					
					if (entity.isPassenger()) {
						player.dismountTo(getX(), getY(), getZ());
					} else {
						entity.teleportTo(getX(), getY(), getZ());
					}
					
					entity.fallDistance = 0;
					entity.hurt(damageSources().fall(), 2);
				}
			} else if (entity != null) {
				entity.teleportTo(getX(), getY(), getZ());
				entity.fallDistance = 0;
			}
			discard();
		}
		
	}
	
	@Override
	public void tick() {
		final Entity entity = getOwner();
		if (entity instanceof Player && !entity.isAlive()) {
			discard();
		} else {
			super.tick();
		}
		
	}
	
}

package info.u_team.u_team_test.entity;

import javax.annotation.Nullable;

import info.u_team.u_team_test.init.TestEntityTypes;
import info.u_team.u_team_test.init.TestItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class BetterEnderPearlEntity extends ThrowableItemProjectile {
	
	public BetterEnderPearlEntity(EntityType<? extends BetterEnderPearlEntity> type, Level world) {
		super(type, world);
	}
	
	public BetterEnderPearlEntity(Level world, LivingEntity thrower) {
		super(TestEntityTypes.BETTER_ENDERPEARL.get(), thrower, world);
	}
	
	@Override
	protected float getGravity() {
		return 0.05F;
	}
	
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		result.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
	}
	
	protected void onHit(HitResult result) {
		super.onHit(result);
		
		for (int i = 0; i < 32; ++i) {
			this.level.addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0D, this.getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
		}
		
		if (!this.level.isClientSide && !this.isRemoved()) {
			Entity entity = this.getOwner();
			if (entity instanceof ServerPlayer) {
				ServerPlayer serverplayer = (ServerPlayer) entity;
				if (serverplayer.connection.getConnection().isConnected() && serverplayer.level == this.level && !serverplayer.isSleeping()) {
					if (this.random.nextFloat() < 0.05F && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
						Endermite endermite = EntityType.ENDERMITE.create(this.level);
						endermite.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
						this.level.addFreshEntity(endermite);
					}
					
					if (entity.isPassenger()) {
						serverplayer.dismountTo(this.getX(), this.getY(), this.getZ());
					} else {
						entity.teleportTo(this.getX(), this.getY(), this.getZ());
					}
					
					entity.teleportTo(this.getX(), this.getY(), this.getZ());
					entity.fallDistance = 0.0F;
					entity.hurt(DamageSource.FALL, 2);
				}
			} else if (entity != null) {
				entity.teleportTo(this.getX(), this.getY(), this.getZ());
				entity.fallDistance = 0.0F;
			}
			
			this.discard();
		}
		
	}
	
	public void tick() {
		Entity entity = this.getOwner();
		if (entity instanceof Player && !entity.isAlive()) {
			this.discard();
		} else {
			super.tick();
		}
		
	}
	
	@Nullable
	public Entity changeDimension(ServerLevel p_37506_, net.minecraftforge.common.util.ITeleporter teleporter) {
		Entity entity = this.getOwner();
		if (entity != null && entity.level.dimension() != p_37506_.dimension()) {
			this.setOwner((Entity) null);
		}
		
		return super.changeDimension(p_37506_, teleporter);
	}
	
	@Override
	protected Item getDefaultItem() {
		return TestItems.BETTER_ENDERPEARL.get();
	}
	
	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}

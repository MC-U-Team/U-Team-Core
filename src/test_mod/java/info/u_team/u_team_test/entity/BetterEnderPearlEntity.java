package info.u_team.u_team_test.entity;

import javax.annotation.Nullable;

import info.u_team.u_team_test.init.*;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkHooks;

public class BetterEnderPearlEntity extends ProjectileItemEntity {
	
	private LivingEntity thrower;
	
	public BetterEnderPearlEntity(EntityType<? extends BetterEnderPearlEntity> type, World world) {
		super(type, world);
	}
	
	public BetterEnderPearlEntity(World world, LivingEntity thrower) {
		super(TestEntityTypes.BETTER_ENDERPEARL.get(), thrower, world);
		this.thrower = thrower;
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.05F;
	}
	
	@Override
	protected void onImpact(RayTraceResult result) {
		final LivingEntity livingentity = this.getThrower();
		if (result.getType() == RayTraceResult.Type.ENTITY) {
			final Entity entity = ((EntityRayTraceResult) result).getEntity();
			if (entity == this.thrower) {
				return;
			}
			
			entity.attackEntityFrom(DamageSource.causeThrownDamage(this, livingentity), 0.0F);
		}
		
		if (result.getType() == RayTraceResult.Type.BLOCK) {
			final BlockPos blockpos = ((BlockRayTraceResult) result).getPos();
			final TileEntity tileentity = this.world.getTileEntity(blockpos);
			if (tileentity instanceof EndGatewayTileEntity) {
				final EndGatewayTileEntity endgatewaytileentity = (EndGatewayTileEntity) tileentity;
				if (livingentity != null) {
					if (livingentity instanceof ServerPlayerEntity) {
						CriteriaTriggers.ENTER_BLOCK.trigger((ServerPlayerEntity) livingentity, this.world.getBlockState(blockpos));
					}
					
					endgatewaytileentity.teleportEntity(livingentity);
					this.remove();
					return;
				}
				
				endgatewaytileentity.teleportEntity(this);
				return;
			}
		}
		
		for (int i = 0; i < 32; ++i) {
			this.world.addParticle(ParticleTypes.PORTAL, this.getPosX(), this.getPosY() + this.rand.nextDouble() * 2.0D, this.getPosZ(), this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
		}
		
		if (!this.world.isRemote) {
			if (livingentity instanceof ServerPlayerEntity) {
				final ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) livingentity;
				if (serverplayerentity.connection.getNetworkManager().isChannelOpen() && serverplayerentity.world == this.world && !serverplayerentity.isSleeping()) {
					final net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(serverplayerentity, this.getPosX(), this.getPosY(), this.getPosZ(), 5.0F);
					if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) {
						if (this.rand.nextFloat() < 0.05F && this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
							final EndermiteEntity endermiteentity = EntityType.ENDERMITE.create(this.world);
							endermiteentity.setSpawnedByPlayer(true);
							endermiteentity.setLocationAndAngles(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ(), livingentity.rotationYaw, livingentity.rotationPitch);
							this.world.addEntity(endermiteentity);
						}
						
						if (livingentity.isPassenger()) {
							livingentity.stopRiding();
						}
						
						livingentity.setPositionAndUpdate(event.getTargetX(), event.getTargetY(), event.getTargetZ());
						livingentity.fallDistance = 0.0F;
						livingentity.attackEntityFrom(DamageSource.FALL, event.getAttackDamage());
					}
				}
			} else if (livingentity != null) {
				livingentity.setPositionAndUpdate(this.getPosX(), this.getPosY(), this.getPosZ());
				livingentity.fallDistance = 0.0F;
			}
			
			this.remove();
		}
		
	}
	
	@Override
	public void tick() {
		final LivingEntity livingentity = this.getThrower();
		if (livingentity != null && livingentity instanceof PlayerEntity && !livingentity.isAlive()) {
			this.remove();
		} else {
			super.tick();
		}
		
	}
	
	@Override
	@Nullable
	public Entity changeDimension(DimensionType destination) {
		if (this.owner.dimension != destination) {
			this.owner = null;
		}
		
		return super.changeDimension(destination);
	}
	
	@Override
	protected Item getDefaultItem() {
		return TestItems.BETTER_ENDERPEARL.get();
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}

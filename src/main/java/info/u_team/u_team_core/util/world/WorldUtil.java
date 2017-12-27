package info.u_team.u_team_core.util.world;

import net.minecraft.block.BlockAir;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;

/**
 * World utility
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 *
 */
public class WorldUtil {
	
	public static boolean isBlockOver(World world, BlockPos blockpos) {
		for (int i = 0; i <= 256; i++) {
			if (!(world.getBlockState(blockpos.up(i)).getBlock() instanceof BlockAir)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNight(World world) {
		return !world.isDaytime();
	}
	
	public static EntityLivingBase getClosestPlayerExpect(World world, EntityLivingBase expect, BlockPos pos, double distance) {
		EntityLivingBase base = null;
		double d = -1;
		for (Object obj : world.loadedEntityList) {
			if (obj instanceof EntityLivingBase) {
				EntityLivingBase entitylivingbase = (EntityLivingBase) obj;
				if (expect != null && entitylivingbase.getUniqueID().equals(expect.getUniqueID())) {
					continue;
				}
				if (entitylivingbase instanceof EntityArmorStand || entitylivingbase.isEntityInvulnerable(DamageSource.generic)) {
					continue;
				}
				double d2 = entitylivingbase.getPosition().distanceSq(pos);
				if ((distance < 0.0D || d2 < distance * distance) && (d == -1.0D || d2 < d)) {
					d = d2;
					base = entitylivingbase;
				}
				
			}
		}
		return base;
	}
	
	public MovingObjectPosition rayTrace(EntityPlayer player, double range) {
		Vec3 playervec = new Vec3(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ);
		Vec3 lookvec = player.getLookVec();
		Vec3 finalvec = playervec.addVector(lookvec.xCoord * range, lookvec.yCoord * range, lookvec.zCoord * range);
		return player.worldObj.rayTraceBlocks(playervec, finalvec, true, false, true);
	}
}

package info.u_team.u_team_core.util.world;

import net.minecraft.block.BlockAir;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class WorldUtil {
	
	public static boolean isBlockOver(World world, BlockPos blockpos) {
		for (int i = 0; i <= 256; i++) {
			if (!(world.getBlockState(blockpos.up(i)).getBlock() instanceof BlockAir)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNight(World w) {
		WorldServer server = FMLCommonHandler.instance().getMinecraftServerInstance().worldServers[0];
		return !server.isDaytime();
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
	
	public static RayTraceResult rayTraceServerSide(EntityPlayer player, double range) {
		Vec3d vec3 = new Vec3d(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ);
		Vec3d vec31 = player.getLookVec();
		Vec3d vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);
		return player.worldObj.rayTraceBlocks(vec3, vec32, true, false, true);
	}
}

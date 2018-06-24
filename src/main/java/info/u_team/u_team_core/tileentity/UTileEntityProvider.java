package info.u_team.u_team_core.tileentity;

import info.u_team.u_team_core.UCoreConstants;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Block API<br>
 * -> TileEntity Holder
 * 
 * @author HyCraftHD
 * @date 21.01.2018
 *
 */

public class UTileEntityProvider {
	
	private Class<? extends TileEntity> tileentity;
	private Object objects[];
	
	public UTileEntityProvider(String name, Class<? extends TileEntity> tileentity, Object... objects) {
		this(name, true, tileentity, objects);
	}
	
	@SuppressWarnings("deprecation")
	public UTileEntityProvider(String name, boolean shouldRegister, Class<? extends TileEntity> tileentity, Object... objects) {
		this.tileentity = tileentity;
		this.objects = objects;
		
		if (shouldRegister) {
			GameRegistry.registerTileEntity(tileentity, name);
		}
	}
	
	public TileEntity create(World world, int meta) {
		try {
			if (objects == null || objects.length == 0) {
				return tileentity.newInstance();
			} else {
				Class<?>[] classes = new Class<?>[objects.length];
				int i = 0;
				for (Object object : objects) {
					classes[i] = object.getClass();
					i++;
				}
				tileentity.getConstructor(classes).newInstance(objects);
			}
		} catch (Exception ex) {
			UCoreConstants.LOGGER.error("Couldn't create tileentity object.", ex);
		}
		return null;
	}
}

package test;

import info.u_team.u_team_core.tileentity.UTileEntityAsyncUpdate;
import info.u_team.u_team_core.util.MathUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.*;

public class TileEntityAsyncTest extends UTileEntityAsyncUpdate {
	
	private BlockPos loc;
	
	@Override
	public void updateAsync() {
		if (world.isRemote)
			return;
		System.out.println("hello me getting updated async");
		
		if (loc == null) {
			loc = pos.add(0, 3, 0);
		}
		
		world.setBlockState(loc, Blocks.STAINED_GLASS.getStateFromMeta(5));
		loc = loc.add(MathUtil.getRandomNumberInRange(-1, 1), MathUtil.getRandomNumberInRange(0, 1), MathUtil.getRandomNumberInRange(-1, 1));
		
		TileEntity tile = world.getTileEntity(pos.add(0, 1, 0));
		if (tile != null) {
			IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
			if (handler != null) {
				handler.insertItem(0, new ItemStack(Blocks.STONE), false);
			}
		}
	}
	
	@Override
	public void update() {
		// System.out.println("sync");
	}
	
	@Override
	public int getUpdateRate() {
		return 250;
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		
	}
	
}

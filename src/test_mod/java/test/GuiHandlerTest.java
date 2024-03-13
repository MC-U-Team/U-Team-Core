package test;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerTest implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == 0) {
			return new ContainerTest(player.inventory, (TileEntityTest) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		if (ID == 1) {
			return new ContainerTestBuggy(player.inventory, (TileEntityTestBuggy) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == 0) {
			return new GuiTest(player.inventory, (TileEntityTest) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		if (ID == 1) {
			return new GuiTestBuggy(player.inventory, (TileEntityTestBuggy) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}
	
}

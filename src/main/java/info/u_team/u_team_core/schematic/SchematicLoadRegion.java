package info.u_team.u_team_core.schematic;

import net.minecraft.nbt.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SchematicLoadRegion {
	
	private World world;
	private BlockPos start;
	
	private int sizex, sizey, sizez, count;
	
	private boolean centerstart = false;
	private boolean rotate = false;
	
	public SchematicLoadRegion(World world, BlockPos start) {
		this.world = world;
		this.start = start;
	}
	
	public SchematicLoadRegion center() {
		centerstart = true;
		return this;
	}
	
	public SchematicLoadRegion rotate() {
		rotate = true;
		return this;
	}
	
	public World getWorld() {
		return world;
	}
	
	public BlockPos getStart() {
		return start;
	}
	
	public void readNBT(NBTTagCompound root) {
		sizex = root.getInteger("sizex");
		sizey = root.getInteger("sizey");
		sizez = root.getInteger("sizez");
		count = root.getInteger("count");
		
		readBlocks(root.getTagList("blocks", 10));
	}
	
	private void readBlocks(NBTTagList list) {
		//TODO rotate and center
		int i = 0;
		for (int x = 0; x < sizex; x++) {
			for (int z = 0; z < sizez; z++) {
				for (int y = 0; y < sizey; y++) {
					BlockPos pos = start.add(x, y, y);
					SchematicEntry entry = new SchematicEntry(list.getCompoundTagAt(i++));
					entry.setBlock(world, pos);
				}
			}
		}
	}
	
}

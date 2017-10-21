package info.u_team.u_team_core.schematic;

import net.minecraft.nbt.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class SchematicLoadRegion {
	
	private World world;
	private BlockPos start;
	
	private int sizex, sizey, sizez;
	
	private boolean centerstart = false;
	private Rotation rotation = Rotation.ROTATION_0;
	
	public SchematicLoadRegion(World world, BlockPos start) {
		this.world = world;
		this.start = start;
	}
	
	public SchematicLoadRegion center() {
		centerstart = true;
		return this;
	}
	
	public SchematicLoadRegion rotate(Rotation rotation) {
		this.rotation = rotation;
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
		
		readBlocks(root.getTagList("blocks", 10));
	}
	
	private void readBlocks(NBTTagList list) {
		
		if (centerstart) {
			centerstart();
		}
		
		int i = 0;
		for (int x = 0; x < sizex; x++) {
			for (int z = 0; z < sizez; z++) {
				for (int y = 0; y < sizey; y++) {
					BlockPos pos = start.add(rotate(new BlockPos(x, y, z)));
					
					SchematicEntry entry = new SchematicEntry(list.getCompoundTagAt(i++));
					entry.setBlock(world, pos);
				}
			}
		}
	}
	
	private void centerstart() {
		switch (rotation) {
		case ROTATION_0:
			start = start.subtract(new Vec3i(sizex / 2, 0, sizez / 2));
			break;
		case ROTATION_90:
			start = start.subtract(new Vec3i(-sizez / 2, 0, sizex / 2));
			break;
		case ROTATION_180:
			start = start.subtract(new Vec3i(-sizex / 2, 0, -sizez / 2));
			break;
		case ROTATION_270:
			start = start.subtract(new Vec3i(sizez / 2, 0, -sizex / 2));
			break;
		}
	}
	
	private BlockPos rotate(BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		switch (rotation) {
		case ROTATION_0:
			return pos;
		case ROTATION_90:
			return new BlockPos(-z, y, x);
		case ROTATION_180:
			return new BlockPos(-x, y, -z);
		case ROTATION_270:
			return new BlockPos(z, y, -x);
		}
		return pos;
	}
	
}

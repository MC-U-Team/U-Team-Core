package info.u_team.u_team_core.schematic;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Deprecated
public class SchematicSaveEntry {
	
	private ResourceLocation registryname;
	private int meta;
	private NBTTagCompound nbt;
	
	public SchematicSaveEntry(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		
		registryname = Block.REGISTRY.getNameForObject(block);
		meta = block.getMetaFromState(state);
		
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity != null) {
			nbt = tileentity.writeToNBT(new NBTTagCompound());
			nbt.removeTag("x"); // We don't need the old location
			nbt.removeTag("y");
			nbt.removeTag("z");
		}
	}
	
	public NBTTagCompound getTag() {
		NBTTagCompound entry = new NBTTagCompound();
		
		entry.setString("name", registryname.toString());
		entry.setInteger("meta", meta);
		if (nbt != null) {
			entry.setTag("nbt", nbt);
		}
		return entry;
	}
	
}

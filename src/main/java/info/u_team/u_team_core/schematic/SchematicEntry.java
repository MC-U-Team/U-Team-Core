package info.u_team.u_team_core.schematic;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SchematicEntry {
	
	private ResourceLocation registryname;
	private int meta;
	private NBTBase nbt;
	
	public SchematicEntry(BlockPos pos, World world) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		
		registryname = Block.REGISTRY.getNameForObject(block);
		meta = block.getMetaFromState(state);
		
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity != null) {
			nbt = tileentity.writeToNBT(new NBTTagCompound());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"name\":");
		builder.append("\"");
		builder.append(registryname);
		builder.append("\"");
		if (meta != 0) {
			builder.append(",\"meta\":");
			builder.append(meta);
		}
		if (nbt != null) {
			builder.append(",\"nbt\":");
			builder.append("\"");
			builder.append(nbt.toString().replaceAll("\\r|\\n", ""));
			builder.append("\"");
		}
		builder.append("}");
		return builder.toString();
	}
	
}

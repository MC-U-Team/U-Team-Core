package info.u_team.u_team_core.schematic;

import info.u_team.u_team_core.intern.UCoreConstants;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

/**
 * Schematic API<br>
 * -> Entry
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public class USchematicEntry {
	
	private ResourceLocation registryname;
	private int meta;
	private NBTTagCompound nbt;
	
	public USchematicEntry(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		
		registryname = (ResourceLocation) Block.blockRegistry.getNameForObject(block);
		meta = block.getMetaFromState(state);
		
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity != null) {
			tileentity.writeToNBT(nbt = new NBTTagCompound());
			nbt.removeTag("x"); // We don't need the old location
			nbt.removeTag("y");
			nbt.removeTag("z");
		}
	}
	
	public USchematicEntry(NBTTagCompound tag) {
		registryname = new ResourceLocation(tag.getString("name"));
		meta = tag.getInteger("meta");
		nbt = tag.getCompoundTag("nbt");
	}
	
	public void setBlock(World world, BlockPos pos) {
		Block block = (Block) Block.blockRegistry.getObject(registryname);
		if (block == null) {
			UCoreConstants.LOGGER.warn("Block registryname " + registryname + " in schematic was not found in minecraft!? Mods missing?");
			block = Blocks.air;
		}
		
		IBlockState state = block.getStateFromMeta(meta);
		world.setBlockState(pos, state);
		
		TileEntity tileentity = world.getTileEntity(pos);
		
		if (tileentity != null && nbt != null) {
			nbt.setInteger("x", pos.getX());
			nbt.setInteger("y", pos.getY());
			nbt.setInteger("z", pos.getZ());
			tileentity.readFromNBT(nbt);
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

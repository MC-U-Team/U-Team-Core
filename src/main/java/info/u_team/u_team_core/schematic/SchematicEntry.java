package info.u_team.u_team_core.schematic;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//Maybe unnessesary because i'm trying to implement a stream version, to not use the ram to much, but we will see ;)
@Deprecated

public class SchematicEntry {
	
	private String registry;
	private int meta;
	private NBTTagCompound tag;
	
	public SchematicEntry(String registry) {
		this(registry, 0, null);
	}
	
	public SchematicEntry(String registry, int meta) {
		this(registry, meta, null);
	}
	
	public SchematicEntry(String registry, NBTTagCompound tag) {
		this(registry, 0, tag);
	}
	
	public SchematicEntry(String registry, int meta, NBTTagCompound tag) {
		this.registry = registry;
		this.meta = meta;
		this.tag = tag;
	}
	
	public String getRegistry() {
		return registry;
	}
	
	public int getMeta() {
		return meta;
	}
	
	public NBTTagCompound getTag() {
		return tag;
	}
	
	public void toBlock(World world, BlockPos pos) {
		Block block = Block.REGISTRY.getObject(new ResourceLocation(registry));
		if (block == null) {
			throw new InvalidSchematicEntryException("Registryname " + registry + " name not found was not found in current minecraft. Mods missing?");
		}
		@SuppressWarnings("deprecation")
		IBlockState state = block.getStateFromMeta(meta);
		world.setBlockState(pos, state, 3);
		
		if (tag == null) {
			return;
		}
		
		TileEntity tileentity = world.getTileEntity(pos);
		
		if (tileentity == null) {
			throw new InvalidSchematicEntryException("Block " + registry + "  is stored with tileentity information, but has no tileentity??");
		}
		
		tag.setInteger("x", pos.getX());
		tag.setInteger("y", pos.getY());
		tag.setInteger("z", pos.getZ());
		
		tileentity.readFromNBT(tag);
		
	}
}

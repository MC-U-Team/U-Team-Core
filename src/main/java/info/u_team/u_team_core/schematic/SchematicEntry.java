package info.u_team.u_team_core.schematic;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.network.ServerToClientConnectionEstablishedHandler;
import net.minecraftforge.common.util.*;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.server.FMLServerHandler;
import net.minecraftforge.server.ForgeTimeTracker;

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
		
		FakePlayer fake = FakePlayerFactory.getMinecraft(DimensionManager.getWorld(0));
		
		Block block = Block.getBlockFromName(registry);
		
		if (block == null) {
			throw new InvalidSchematicEntryException("Registryname " + registry + " name not found was not found in current minecraft. Mods missing?");
		}
		
		
		world.setBlockState(pos, block.getStateFromMeta(meta), 1);
		FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendPacketToAllPlayers(new SPacketBlockChange(world, pos));
		
//		Chunk chunk = world.getChunkFromBlockCoords(pos);
//		
//		int i = pos.getX() & 15;
//		int j = pos.getY();
//		int k = pos.getZ() & 15;
//		
//		ExtendedBlockStorage[] storage = chunk.getBlockStorageArray();
//		
//		System.out.println(storage);
//		System.out.println(j);
//		System.out.println(j >> 4);
//		
//		
//		ExtendedBlockStorage s = storage[j >> 4];
//		
//		if (s == null) {
//			s = new ExtendedBlockStorage(j >> 4, false);
//		}
//		
//		s.set(i, j & 15, k, block.getStateFromMeta(meta));
//		
//		chunk.setStorageArrays(storage);
//		
//		chunk.setModified(true);
//		chunk.onTick(true);
//		
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
	
	private IBlockState placeBlockAt(World world, BlockPos pos, ItemStack stack, Integer origMeta, FakePlayer fakePlayer) {
		Item item = stack.getItem();
		if (item instanceof ItemBlock) {
			ItemBlock itemBlock = (ItemBlock) item;
			if (origMeta == null) {
				origMeta = itemBlock.getDamage(stack);
			}
			IBlockState newState = itemBlock.block.getStateFromMeta(origMeta);
			itemBlock.placeBlockAt(stack, fakePlayer, world, pos, EnumFacing.UP, 0, 0, 0, newState);
			return newState;
		} else {
			item.onItemUse(stack, fakePlayer, world, pos.down(), EnumHand.MAIN_HAND, EnumFacing.UP, 0, 0, 0);
			return world.getBlockState(pos);
		}
	}
}

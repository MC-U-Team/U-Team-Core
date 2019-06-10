package info.u_team.u_team_core.block;

import org.apache.commons.lang3.tuple.Pair;

import info.u_team.u_team_core.api.ISyncedContainerTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraftforge.fml.network.NetworkHooks;

public class UBlockTileEntity extends UBlock {
	
	protected final TileEntityType<?> tileentitytype;
	
	public UBlockTileEntity(String name, Properties properties, TileEntityType<?> tileentitytype) {
		this(name, null, properties, tileentitytype);
	}
	
	public UBlockTileEntity(String name, ItemGroup group, Properties properties, TileEntityType<?> tileentitytype) {
		this(name, group, properties, null, tileentitytype);
	}
	
	public UBlockTileEntity(String name, Properties properties, Item.Properties itemblockproperties, TileEntityType<?> tileentitytype) {
		this(name, null, properties, itemblockproperties, tileentitytype);
	}
	
	public UBlockTileEntity(String name, ItemGroup group, Properties properties, Item.Properties itemblockproperties, TileEntityType<?> tileentitytype) {
		super(name, group, properties, itemblockproperties);
		this.tileentitytype = tileentitytype;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return tileentitytype.create();
	}
	
	public boolean openContainer(World world, BlockPos pos, ServerPlayerEntity player) {
		return openContainer(world, pos, player, false);
	}
	
	public boolean openContainer(World world, BlockPos pos, ServerPlayerEntity player, boolean canOpenSneak) {
		if (world.isRemote || !(player instanceof ServerPlayerEntity)) {
			// Need to return true here, cause else it will create two instances of our gui
			// which may cause bugs. The method onBlockActivated must return this value
			// correctly!
			return true;
		}
		ServerPlayerEntity playermp = (ServerPlayerEntity) player;
		Pair<Boolean, TileEntity> pair = isTileEntityFromProvider(world, pos);
		if (!pair.getLeft()) {
			return false;
		}
		TileEntity tileentity = pair.getRight();
		
		if (!(tileentity instanceof INamedContainerProvider)) {
			return false;
		}
		if (!canOpenSneak && playermp.isSneaking()) {
			return true;
		}
		
		CompoundNBT compound = null;
		if (tileentity instanceof ISyncedContainerTileEntity) {
			compound = new CompoundNBT();
			((ISyncedContainerTileEntity) tileentity).writeOnGuiOpenServer(compound);
		}
		
		final CompoundNBT finalCompound = compound;
		NetworkHooks.openGui(playermp, (INamedContainerProvider) tileentity, buf -> {
			buf.writeBlockPos(pos);
			if (finalCompound != null) {
				buf.writeCompoundTag(finalCompound);
			}
		});
		return true;
		
	}
	
	public Pair<Boolean, TileEntity> isTileEntityFromProvider(IBlockReader world, BlockPos pos) {
		TileEntity tileentity = world.getTileEntity(pos);
		boolean isValid = tileentity != null && tileentitytype == tileentity.getType();
		return Pair.of(isValid, tileentity);
	}
	
}

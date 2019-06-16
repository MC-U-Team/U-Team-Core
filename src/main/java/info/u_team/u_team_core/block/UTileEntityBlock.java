package info.u_team.u_team_core.block;

import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import info.u_team.u_team_core.api.ISyncedContainerTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraftforge.fml.network.NetworkHooks;

public class UTileEntityBlock extends UBlock {
	
	protected final Supplier<TileEntityType<?>> tileentitytype;
	
	public UTileEntityBlock(String name, Properties properties, Supplier<TileEntityType<?>> tileentitytype) {
		this(name, null, properties, tileentitytype);
	}
	
	public UTileEntityBlock(String name, ItemGroup group, Properties properties, Supplier<TileEntityType<?>> tileentitytype) {
		this(name, group, properties, null, tileentitytype);
	}
	
	public UTileEntityBlock(String name, Properties properties, Item.Properties itemblockproperties, Supplier<TileEntityType<?>> tileentitytype) {
		this(name, null, properties, itemblockproperties, tileentitytype);
	}
	
	public UTileEntityBlock(String name, ItemGroup group, Properties properties, Item.Properties itemblockproperties, Supplier<TileEntityType<?>> tileentitytype) {
		super(name, group, properties, itemblockproperties);
		this.tileentitytype = tileentitytype;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return tileentitytype.get().create();
	}
	
	public boolean openContainer(World world, BlockPos pos, PlayerEntity player) {
		return openContainer(world, pos, player, false);
	}
	
	public boolean openContainer(World world, BlockPos pos, PlayerEntity player, boolean canOpenSneak) {
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
		
		 Container c = ((INamedContainerProvider)tileentity).createMenu(playermp.currentWindowId, player.inventory, player);
	        ContainerType<?> type = c.getType();
	        System.out.println(type.getRegistryName());
		
		
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
		boolean isValid = tileentity != null && tileentitytype.get() == tileentity.getType();
		return Pair.of(isValid, tileentity);
	}
	
}

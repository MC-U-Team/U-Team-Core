package info.u_team.u_team_core.block;

import org.apache.commons.lang3.tuple.Pair;

import info.u_team.u_team_core.api.ISyncedContainerTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.nbt.NBTTagCompound;
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
		super(name, group, properties);
		this.tileentitytype = tileentitytype;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
		return tileentitytype.create();
	}
	
	public boolean openContainer(World world, BlockPos pos, EntityPlayer player) {
		return openContainer(world, pos, player, false);
	}
	
	public boolean openContainer(World world, BlockPos pos, EntityPlayer player, boolean canOpenSneak) {
		if (world.isRemote || !(player instanceof EntityPlayerMP)) {
			// Need to return true here, cause else it will create two instances of our gui
			// which may cause bugs. The method onBlockActivated must return this value
			// correctly!
			return true;
		}
		EntityPlayerMP playermp = (EntityPlayerMP) player;
		Pair<Boolean, TileEntity> pair = isTileEntityFromProvider(world, pos);
		if (!pair.getLeft()) {
			return false;
		}
		TileEntity tileentity = pair.getRight();
		
		if (!(tileentity instanceof IInteractionObject)) {
			return false;
		}
		if (!canOpenSneak && playermp.isSneaking()) {
			return true;
		}
		
		NBTTagCompound compound = null;
		if (tileentity instanceof ISyncedContainerTileEntity) {
			compound = new NBTTagCompound();
			((ISyncedContainerTileEntity) tileentity).writeOnGuiOpenServer(compound);
		}
		
		final NBTTagCompound finalCompound = compound;
		NetworkHooks.openGui(playermp, (IInteractionObject) tileentity, buf -> {
			buf.writeBlockPos(pos);
			if (finalCompound != null) {
				buf.writeCompoundTag(finalCompound);
			}
		});
		playermp.sendAllContents(playermp.openContainer, playermp.openContainer.getInventory()); // not indended i think. Should be changed ? New forge version? //TODO
		return true;
		
	}
	
	public Pair<Boolean, TileEntity> isTileEntityFromProvider(IBlockReader world, BlockPos pos) {
		TileEntity tileentity = world.getTileEntity(pos);
		boolean isValid = tileentity != null && tileentitytype == tileentity.getType();
		return Pair.of(isValid, tileentity);
	}
	
}

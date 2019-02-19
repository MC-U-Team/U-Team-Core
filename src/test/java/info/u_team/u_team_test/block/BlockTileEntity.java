package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_test.container.ContainerTileEntity;
import info.u_team.u_team_test.init.TestItemGroups;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.*;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockTileEntity extends UBlock {
	
	public BlockTileEntity(String name) {
		super(name, TestItemGroups.group, Properties.create(Material.ROCK).hardnessAndResistance(2F).sound(SoundType.GROUND).slipperiness(0.8F).lightValue(1));
	}
	
	@Override
	public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		NetworkHooks.openGui((EntityPlayerMP) player, new IInteractionObject() {
			
			@Override
			public boolean hasCustomName() {
				return false;
			}
			
			@Override
			public ITextComponent getName() {
				return null;
			}
			
			@Override
			public ITextComponent getCustomName() {
				return null;
			}
			
			@Override
			public String getGuiID() {
				return "uteamtest:test";
			}
			
			@Override
			public Container createContainer(InventoryPlayer playerinventory, EntityPlayer player) {
				return new ContainerTileEntity(playerinventory);
			}
		});
		return true;
	}
	
}

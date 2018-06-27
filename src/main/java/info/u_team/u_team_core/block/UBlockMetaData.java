package info.u_team.u_team_core.block;

import java.util.*;

import info.u_team.u_team_core.api.IUMetaType;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.item.*;
import info.u_team.u_team_core.tileentity.UTileEntityMetaData;
import info.u_team.u_team_core.util.CustomResourceLocation;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Block API<br>
 * -> Metadata Blocks
 * 
 * @date 26.06.2018
 * @author HyCraftHD
 */
public class UBlockMetaData extends UBlock implements ITileEntityProvider {
	
	private NonNullList<IUMetaType> list;
	private boolean registered;
	
	public UBlockMetaData(String name, Material material, NonNullList<IUMetaType> list) {
		this(name, material, null, list);
	}
	
	public UBlockMetaData(String name, Material material, UCreativeTab tab, NonNullList<IUMetaType> list) {
		super(name, material, tab);
		this.list = list;
		hasTileEntity = true;
		registered = false;
	}
	
	@Override
	public UItemBlock getItemBlock() {
		return new UItemBlockMetaData(this, list);
	}
	
	@Override
	public void registerModel() {
		for (int i = 0; i < list.size(); i++) {
			setModel(getItem(), i, new CustomResourceLocation(getRegistryName(), "/" + list.get(i).getName()));
		}
		ModelLoader.setCustomMeshDefinition(item, meshDefinition);
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity == null) {
			return;
		}
		if (world.isRemote) {
			return;
		}
		if (!(tileentity instanceof UTileEntityMetaData)) {
			return;
		}
		((UTileEntityMetaData) tileentity).setMeta(stack.getMetadata());
		tileentity.markDirty();
	}
	
	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileentity, ItemStack stack) {
		
		int meta = 0;
		if (tileentity instanceof UTileEntityMetaData) {
			meta = ((UTileEntityMetaData) tileentity).getMeta();
		}
		
		player.addStat(StatList.getBlockStats(this));
		player.addExhaustion(0.005F);
		
		if (this.canSilkHarvest(worldIn, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0) {
			java.util.List<ItemStack> items = new java.util.ArrayList<ItemStack>();
			ItemStack itemstack = this.getSilkTouchDrop(state);
			
			if (!itemstack.isEmpty()) {
				items.add(itemstack);
			}
			
			net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
			for (ItemStack item : items) {
				if (Block.getBlockFromItem(item.getItem()) == this) {
					item.setItemDamage(meta);
				}
				spawnAsEntity(worldIn, pos, item);
			}
		} else {
			harvesters.set(player);
			int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
			
			float chance = 1.0F;
			int fortune = i;
			
			if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) {
				
				NonNullList<ItemStack> drops = NonNullList.create();
				
				getDrops(drops, worldIn, pos, state, fortune);
				chance = net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(drops, worldIn, pos, state, fortune, chance, false, harvesters.get());
				
				for (ItemStack drop : drops) {
					if (worldIn.rand.nextFloat() <= chance) {
						if (Block.getBlockFromItem(drop.getItem()) == this) {
							drop.setItemDamage(meta);
						}
						spawnAsEntity(worldIn, pos, drop);
					}
				}
			}
			
			harvesters.set(null);
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if (!registered) {
			GameRegistry.registerTileEntity(UTileEntityMetaData.class, getRegistryName());
			registered = true;
		}
		return new UTileEntityMetaData();
	}
	
}

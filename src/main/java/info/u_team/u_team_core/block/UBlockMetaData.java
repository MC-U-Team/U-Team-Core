package info.u_team.u_team_core.block;

import java.util.*;
import java.util.stream.Collectors;

import info.u_team.u_team_core.api.IUMetaType;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.item.*;
import info.u_team.u_team_core.property.PropertyList;
import info.u_team.u_team_core.util.CustomResourceLocation;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.*;
import net.minecraft.util.NonNullList;

/**
 * Block API<br>
 * -> Metadata Blocks
 * 
 * @date 26.06.2018
 * @author HyCraftHD
 */
public class UBlockMetaData extends UBlock {
	
	private NonNullList<IUMetaType> list;
	
	public PropertyList<MetaType> test;
	
	private BlockStateContainer blockstate;
	
	private List<MetaType> types = new ArrayList<>();
	
	public UBlockMetaData(String name, Material material, NonNullList<IUMetaType> list) {
		this(name, material, null, list);
	}
	
	public UBlockMetaData(String name, Material material, UCreativeTab tab, NonNullList<IUMetaType> list) {
		super(name, material, tab);
		this.list = list;
		
		for (int i = 0; i < list.size(); i++) {
			types.add(new MetaType(list.get(i), i));
		}
		
		test = PropertyList.create("meta", MetaType.class, types);
		blockstate = new BlockStateContainer(this, test);
		setDefaultState(blockstate.getBaseState());
	}
	
	@Override
	public BlockStateContainer getBlockState() {
		return blockstate;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(test, types.get(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(test).getMeta();
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
	}
	
	public static class MetaType implements IUMetaType, Comparable<MetaType> {
		
		private IUMetaType wrapped;
		
		private int meta;
		
		public MetaType(IUMetaType wrapped, int meta) {
			this.wrapped = wrapped;
			this.meta = meta;
		}
		
		@Override
		public String getName() {
			return wrapped.getName();
		}
		
		public int getMeta() {
			return meta;
		}
		
		@Override
		public int compareTo(MetaType o) {
			return getName().compareTo(o.getName());
		}
		
		@Override
		public boolean equals(Object obj) {
			return getName().equals(((MetaType) obj).getName());
		}
	}
	
}

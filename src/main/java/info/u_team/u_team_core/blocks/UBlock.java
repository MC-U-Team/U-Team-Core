package info.u_team.u_team_core.blocks;

import javax.annotation.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.intern.UCoreConstants;
import info.u_team.u_team_core.item.UItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Block API<br>
 * -> Basic Block
 * 
 * @date 17.08.2017
 * @author MrTroble
 */

public class UBlock extends Block {
	
	public UBlock(@Nonnull Material material, @Nonnull String name, @Nullable UCreativeTab tab) {
		this(material, name, tab, UItemBlock.class);
	}
	
	public UBlock(@Nonnull Material material, @Nonnull String name, @Nullable UCreativeTab tab, @Nullable Class<? extends UItemBlock> itemblock) {
		super(material);
		
		this.setRegistryName(UCoreMain.SUBMOD_MODID, name);
		this.setUnlocalizedName(name);
		
		if (tab != null) {
			this.setCreativeTab(tab);
		}
		
		register(itemblock);
	}
	
	private void register(Class<? extends UItemBlock> itemblock) {
		try {
			GameRegistry.register(this);
			GameRegistry.register(itemblock.getConstructor(UBlock.class).newInstance(this));
		} catch (Exception ex) {
			UCoreConstants.LOGGER.error("Error in Gameregistery", ex);
		}
	}
	
}

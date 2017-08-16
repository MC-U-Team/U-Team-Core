package info.u_team.u_team_core.blocks;

import java.lang.reflect.InvocationTargetException;

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
	
	public UBlock(@Nonnull Material materialIn, @Nonnull String name, @Nullable UCreativeTab tab) {
		this(materialIn, name, tab, UItemBlock.class);
	}
	
	public UBlock(@Nonnull Material materialIn, @Nonnull String name, @Nullable UCreativeTab tab, @Nullable Class<? extends UItemBlock> clazz) {
		super(materialIn);
		if (name == null) {
			UCoreConstants.LOGGER.error("Name is null of Class " + this.getClass().getName());
			return;
		}
		this.setRegistryName(UCoreMain.SUBMOD_MODID, name);
		this.setUnlocalizedName(name);
		if (tab != null)
			this.setCreativeTab(tab);
		try {
			GameRegistry.register(this);
			if (clazz != null)
				GameRegistry.register(clazz.getConstructor(UBlock.class).newInstance(this));
			else
				GameRegistry.register(new UItemBlock(this));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			GameRegistry.register(new UItemBlock(this));
			UCoreConstants.LOGGER.warn("Error in Gameregistery", e);
		}
	}
	
}

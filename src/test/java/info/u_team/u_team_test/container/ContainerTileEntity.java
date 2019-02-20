package info.u_team.u_team_test.container;

import java.util.List;

import info.u_team.u_team_core.container.*;
import info.u_team.u_team_test.tileentity.TileEntityTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ContainerTileEntity extends UContainerTileEntity {
	
	public ContainerTileEntity(InventoryPlayer inventoryPlayer, TileEntityTileEntity tileentity) {
		super(tileentity);
		
		IInventory inventoryBasic = tileentity;
		
		for (int height = 0; height < 2; height++) {
			for (int width = 0; width < 9; width++) {
				addSlot(new Slot(inventoryBasic, width + height * 9, width * 18 + 5, height * 18 + 5));
			}
		}
		
		appendPlayerInventory(inventoryPlayer, 5, 80);
		
	}
	
	@Override
	public void setAll(List<ItemStack> p_190896_1_) {
		System.out.println("SET STACKS ON CLIENT");
		
		System.out.println(p_190896_1_);
		System.out.println(p_190896_1_.size());
		
		super.setAll(p_190896_1_);
	}
	
	@Override
	public NonNullList<ItemStack> getInventory() {
		System.out.println("GET STACKS ON SERVER");
		NonNullList<ItemStack> stacks = super.getInventory();
		
		System.out.println(stacks);
		System.out.println(stacks.size());
		
		return stacks;
	}
	
}

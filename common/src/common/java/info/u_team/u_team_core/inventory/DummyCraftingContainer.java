package info.u_team.u_team_core.inventory;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;

public class DummyCraftingContainer extends TransientCraftingContainer {
	
	public DummyCraftingContainer(int width, int height) {
		super(new AbstractContainerMenu(null, -1) {
			
			@Override
			public boolean stillValid(Player player) {
				return false;
			}
			
			@Override
			public ItemStack quickMoveStack(Player player, int slot) {
				return ItemStack.EMPTY;
			}
		}, width, height);
	}
	
}
